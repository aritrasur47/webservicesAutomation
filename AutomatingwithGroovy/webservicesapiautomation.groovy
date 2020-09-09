//Hit addEmployee Request with properties from TestCase Level

import com.eviware.soapui.support.XmlHolder
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext

def addReq = testRunner.testCase.testSuite.testCases["addEmployee"].testSteps["addEmp"].getPropertyValue("Request")

def addName = testRunner.testCase.testSuite.testCases["addEmployee"].getPropertyValue("name")
def addId = testRunner.testCase.testSuite.testCases["addEmployee"].getPropertyValue("id")
def addDept = testRunner.testCase.testSuite.testCases["addEmployee"].getPropertyValue("dept")
def addAge = testRunner.testCase.testSuite.testCases["addEmployee"].getPropertyValue("age")

def xmlAdd = new XmlHolder(addReq)

xmlAdd.setNodeValue("//typ:addEmployee/typ:name", addName)
xmlAdd.setNodeValue("//typ:addEmployee/typ:id", addId)
xmlAdd.setNodeValue("//typ:addEmployee/typ:age", addAge)
xmlAdd.setNodeValue("//typ:addEmployee/typ:Department", addDept)

def newUpdatedXml = xmlAdd.getXml();

testRunner.testCase.testSuite.testCases["addEmployee"].testSteps["addEmp"].setPropertyValue("Request", newUpdatedXml)

def addTestStep = testRunner.testCase.testSuite.testCases["addEmployee"].testSteps["addEmp"]
def contextAddEmployee = new WsdlTestRunContext(addTestStep);
addTestStep.run(testRunner, contextAddEmployee)

//Hit getEmployee and generate response

def getReq = testRunner.testCase.testSuite.testCases["getEmployee"].testSteps["getEmp"].getPropertyValue("Request")
def xmlGet = new XmlHolder(getReq)

xmlGet.setNodeValue("//typ:getEmployeeDetails/typ:employeeName", addName)
def newGetXml = xmlGet.getXml();
testRunner.testCase.testSuite.testCases["getEmployee"].testSteps["getEmp"].setPropertyValue("Request", newGetXml)

def getTestStep = testRunner.testCase.testSuite.testCases["getEmployee"].testSteps["getEmp"]
def contextGetEmployee = new WsdlTestRunContext(getTestStep);
getTestStep.run(testRunner, contextGetEmployee)

//Validation
def getResponse = testRunner.testCase.testSuite.testCases["getEmployee"].testSteps["getEmp"].getPropertyValue("Response")
def getEmpResp = new XmlHolder(getResponse)

def gotResp = getEmpResp.getNodeValue("//ns:name")

log.info gotResp 
log.info addName
assert gotResp==addName
