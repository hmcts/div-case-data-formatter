provider "azurerm" {
    features {}
}

data "azurerm_key_vault" "div_key_vault" {
    name                = local.vaultName
    resource_group_name = local.vaultName
}

data "azurerm_key_vault_secret" "appinsights_secret" {
  name = "AppInsightsInstrumentationKey"
  key_vault_id = data.azurerm_key_vault.div_key_vault.id
}
