provider "azurerm" {
  version = "1.44.0"
}

locals {
    ase_name                  = "core-compute-${var.env}"
    local_env                 = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "aat" : "saat" : var.env}"

    dm_store_url              = "http://dm-store-${local.local_env}.service.core-compute-${local.local_env}.internal"

    asp_name = "${var.env == "prod" ? "div-cfs-prod" : "${var.raw_product}-${var.env}"}"
    asp_rg = "${var.env == "prod" ? "div-cfs-prod" : "${var.raw_product}-${var.env}"}"
}

module "div-cfs" {
    source                          = "git@github.com:hmcts/cnp-module-webapp?ref=master"
    product                         = "${var.product}-${var.component}"
    location                        = "${var.location}"
    env                             = "${var.env}"
    ilbIp                           = "${var.ilbIp}"
    subscription                    = "${var.subscription}"
    appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
    is_frontend                     = false
    capacity                        = "${var.capacity}"
    common_tags                     = "${var.common_tags}"
    asp_name                        = "${local.asp_name}"
    asp_rg                          = "${local.asp_rg}"
    instance_size                   = "${var.instance_size}"
    enable_ase                      = "${var.enable_ase}"

    app_settings = {
        REFORM_SERVICE_NAME                                   = "${var.component}"
        REFORM_TEAM                                           = "${var.product}"
        REFORM_ENVIRONMENT                                    = "${var.env}"
        DOCUMENT_MANAGEMENT_STORE_URL                         = "${local.dm_store_url}"
        MANAGEMENT_ENDPOINT_HEALTH_CACHE_TIMETOLIVE           = "${var.health_check_ttl}"
    }
}
