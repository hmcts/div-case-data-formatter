output "test_environment" {
    value = "${local.local_env}"
}

output "auth_idam_client_baseUrl" {
    value = "${var.idam_api_baseurl}"
}

output "document_management_store_url" {
    value = "${local.dm_store_url}"
}
