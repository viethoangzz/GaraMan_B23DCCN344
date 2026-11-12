$ErrorActionPreference = 'Stop'
$base = 'http://localhost:18080/GaraMan'
function Assert-True($condition, $message) { if (-not $condition) { throw $message }; Write-Output "PASS: $message" }
function Login($username) {
    $page = Invoke-WebRequest -UseBasicParsing "$base/login" -SessionVariable userSession -TimeoutSec 15
    $csrf = [regex]::Match($page.Content, 'name="csrf"\s+value="([^"]+)"').Groups[1].Value
    $dashboard = Invoke-WebRequest -UseBasicParsing "$base/login" -WebSession $userSession -Method Post -Body @{username=$username;password='Demo@12345';csrf=$csrf} -TimeoutSec 15
    Assert-True ($dashboard.BaseResponse.ResponseUri.AbsolutePath.EndsWith('/app/dashboard')) "$username login"
    Assert-True ($dashboard.Content -notmatch '/app/(manage|appointments|sales|warehouse)') "$username menu only has retained features"
    return $userSession
}
# Keep progress output separate from the returned session object.
$customerResult = @(Login 'customer'); $customer = $customerResult[-1]; $customerResult[0..($customerResult.Length-2)]
$managerResult = @(Login 'manager'); $manager = $managerResult[-1]; $managerResult[0..($managerResult.Length-2)]
function Page($path, $session) { (Invoke-WebRequest -UseBasicParsing "$base$path" -WebSession $session -TimeoutSec 15).Content }
$results = Page '/app/search?keyword=phanh' $customer
foreach ($code in @('DV005','PT012','PT013')) { Assert-True ($results.Contains($code)) "Search contains $code" }
Assert-True ((Page '/app/detail?type=service&id=5&keyword=phanh' $customer).Contains('Thay má phanh')) 'Service detail'
Assert-True ((Page '/app/detail?type=part&id=12&keyword=phanh' $customer).Contains('Má phanh trước')) 'Part detail'
$reports = Page '/app/reports' $manager
Assert-True ($reports.Contains('/app/reports/customer-revenue') -and $reports -notmatch '/app/reports/(service|part|supplier)') 'Report selection contains only customer revenue'
Assert-True ((Page '/app/reports/customer-revenue' $manager).Contains('name="startDate"')) 'Date range form'
$stats = Page '/app/reports/customer-revenue?startDate=2026-08-01&endDate=2026-08-31' $manager
foreach ($code in @('KH001','KH002','KH003')) { Assert-True ($stats.Contains($code)) "Statistics contains $code" }
$visits = Page '/app/reports/customer-visits?customerId=1&startDate=2026-08-01&endDate=2026-08-31' $manager
foreach ($code in @('LV001','LV007')) { Assert-True ($visits.Contains($code)) "Visits contains $code" }
Assert-True ((Page '/app/reports/invoice?visitId=7&startDate=2026-08-01&endDate=2026-08-31' $manager).Contains('HD007')) 'Invoice detail HD007'
foreach ($path in @('/register','/app/appointments','/app/manage/services','/app/manage/parts','/app/manage/suppliers','/app/warehouse/import','/app/sales/visits','/app/sales/assign','/app/sales/payments','/app/reports/service','/app/reports/part','/app/reports/supplier','/app/reports/customer')) {
    $status=200
    try { $null=Page $path $manager } catch { if ($_.Exception.Response) { $status=[int]$_.Exception.Response.StatusCode } else { throw } }
    Assert-True ($status -eq 404) "Removed route $path returns 404"
}
foreach ($check in @(@('/app/reports',$customer),@('/app/search',$manager))) {
    $status=200
    try { $null=Page $check[0] $check[1] } catch { if ($_.Exception.Response) { $status=[int]$_.Exception.Response.StatusCode } else { throw } }
    Assert-True ($status -eq 403) "Role protection for $($check[0])"
}
