locals {
    ase_name                  = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
    local_env                 = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "aat" : "saat" : var.env}"

    dm_store_url              = "http://dm-store-${local.local_env}.service.core-compute-${local.local_env}.internal"
}

module "div-cfs" {
    source                          = "git@github.com:hmcts/moj-module-webapp.git?ref=master"
    product                         = "${var.product}-${var.component}"
    location                        = "${var.location}"
    env                             = "${var.env}"
    ilbIp                           = "${var.ilbIp}"
    subscription                    = "${var.subscription}"
    appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
    is_frontend                     = false
    capacity                        = "${var.capacity}"
    common_tags                     = "${var.common_tags}"

    app_settings = {
        REFORM_SERVICE_NAME                                   = "${var.component}"
        REFORM_TEAM                                           = "${var.product}"
        REFORM_ENVIRONMENT                                    = "${var.env}"
        IDAM_API_BASEURL                                      = "${var.idam_api_baseurl}"
        DOCUMENT_MANAGEMENT_STORE_URL                         = "${local.dm_store_url}"
    }
}
