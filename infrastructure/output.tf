output "test_environment" {
    value = "${local.local_env}"
}

output "idam_api_baseurl" {
    value = "${var.idam_api_baseurl}"
}

output "document_management_store_url" {
    value = "${local.dm_store_url}"
}
