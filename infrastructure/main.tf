provider "azurerm" {
    features {}
}

data "azurerm_key_vault" "div_key_vault" {
    name                = "${var.product}-${var.env}"
    resource_group_name = "${var.product}-${var.env}"
}

data "azurerm_key_vault_secret" "appinsights_secret" {
  name = "AppInsightsInstrumentationKey"
  key_vault_id = data.azurerm_key_vault.div_key_vault.id
  tags                = var.common_tags
}
