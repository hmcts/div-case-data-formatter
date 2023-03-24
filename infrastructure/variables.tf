variable "product" {}

variable "raw_product" {
    default = "div"
}

variable "component" {}

variable "env" {}

variable "tenant_id" {}

variable "jenkins_AAD_objectId" {
    description = "(Required) The Azure AD object ID of a user, service principal or security group in the Azure Active Directory tenant for the vault. The object ID must be unique for the list of access policies."
}

variable "appinsights_instrumentation_key" {
    description = "Instrumentation key of the App Insights instance this webapp should use. Module will create own App Insights resource if this is not provided"
    default = ""
}

variable "capacity" {
    default = "1"
}

variable "instance_size" {
    default = "I2"
}

variable "subscription" {}

variable "location" {
    default = "UK South"
}

variable "vault_env" {}

variable "common_tags" {
    type = map(string)
}

variable "health_check_ttl" {
    default = "4000"
}

variable "enable_ase" {
    default = false
}
