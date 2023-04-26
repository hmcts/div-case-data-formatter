output "test_environment" {
    value = "${var.env}"
}

output "document_management_store_url" {
    value = "http://dm-store-${var.env}.service.core-compute-${var.env}.internal"
}
