package code.api.v1_4_0

import java.util.Date

import code.bankbranches.BankBranches
import code.bankbranches.BankBranches.{BankBranch, DataLicense, BranchData}
import code.customerinfo.{CustomerMessage, CustomerInfo}

object JSONFactory1_4_0 {

  case class CustomerInfoJson(customer_number : String,
                              legal_name : String,
                              mobile_phone_number : String,
                              email : String,
                              face_image : CustomerFaceImageJson)

  case class CustomerFaceImageJson(url : String, date : Date)

  case class CustomerMessagesJson(messages : List[CustomerMessageJson])
  case class CustomerMessageJson(id : String, date : Date, message : String, from_department : String, from_person : String)

  case class AddCustomerMessageJson(message : String, from_department : String, from_person : String)

  case class BranchDataJson(license : DataLicenseJson, branches : List[BranchJson])
  case class DataLicenseJson(name : String, url : String)
  case class BranchJson(id : String, name : String, address : AddressJson)
  case class AddressJson(line_1 : String, line_2 : String, line_3 : String, line_4 : String, line_5 : String, postcode_zip : String, country : String)

  def createCustomerInfoJson(cInfo : CustomerInfo) : CustomerInfoJson = {

    CustomerInfoJson(customer_number = cInfo.number,
      legal_name = cInfo.legalName, mobile_phone_number = cInfo.mobileNumber,
      email = cInfo.email, face_image = CustomerFaceImageJson(url = cInfo.faceImage.url, date = cInfo.faceImage.date))

  }

  def createCustomerMessageJson(cMessage : CustomerMessage) : CustomerMessageJson = {
    CustomerMessageJson(id = cMessage.messageId, date = cMessage.date,
      message = cMessage.message, from_department = cMessage.fromDepartment,
      from_person = cMessage.fromPerson)
  }

  def createCustomerMessagesJson(messages : List[CustomerMessage]) : CustomerMessagesJson = {
    CustomerMessagesJson(messages.map(createCustomerMessageJson))
  }

  def createDataLicenseJson(dataLicense : DataLicense) : DataLicenseJson = {
    DataLicenseJson(dataLicense.name, dataLicense.url)
  }

  def createAddressJson(address : BankBranches.Address) : AddressJson = {
    AddressJson(address.line1, address.line2, address.line3, address.line4, address.line5, address.postCode, address.countryCode)
  }

  def createBranchJson(bankBranch: BankBranch) : BranchJson = {
    BranchJson(bankBranch.branchId.value, bankBranch.name, createAddressJson(bankBranch.address))
  }

  def createBranchesJson(branchData : BranchData) : BranchDataJson = {
    BranchDataJson(createDataLicenseJson(branchData.license), branchData.branches.map(createBranchJson))
  }

}
