output "test_environment" {
    value = "${local.local_env}"
}

output "auth_idam_client_baseUrl" {
    value = "${var.idam_api_baseurl}"
}
