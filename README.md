## In this Project there are a few things needed to be noted
## We have to disable security by providing the configuration class look carefully at config class under config folder in tests
## When we are using TestRestTemplate with security Spring securityChainFilter generates the endpoint for us. to overcome this problem either we need to use the .withBasicAuth method or disable the security at all
 
## We have to look at the WireMock extension and setup in test class. Wiremocks are dependent on test property file. they need the same properties in appliaation.properties


    