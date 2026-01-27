19 th step 
clike ,clear,send key (123). IN EP-FIELD
in the xpath 
name == "Enter Password"
xpath
//XCUIElementTypeOther[@name="Enter Password"]

20th step 
clike ,clear,send key (12345) IN CP-FIELD
in the xpath 
name == "Confirm Password"
xpath
//XCUIElementTypeOther[@name="Confirm Password"]

21th step 
clike reset password button 
name == "RESET PASSWORD" AND label == "RESET PASSWORD" AND type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton[@name="RESET PASSWORD"]


22th step
get the error message in the xpath AND SHOW IN REPORT
name == "Passwords do not match"
xpath
//XCUIElementTypeStaticText[@name="Passwords do not match"]

23rd step 
clike ,clear,send key (ABC) IN EP-FIELD
in the xpath 
name == "Enter Password"
xpath
//XCUIElementTypeOther[@name="Enter Password"]

24TH STEP
clike ,clear,send key (ABC) IN CP-FIELD
in the xpath 
name == "Confirm Password"
xpath
//XCUIElementTypeOther[@name="Confirm Password"]

25th step 
clike reset password button 
name == "RESET PASSWORD" AND label == "RESET PASSWORD" AND type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton[@name="RESET PASSWORD"]

26th step
get the error message in the xpath AND SHOW IN REPORT ,
name == "Use at least 8 characters with uppercase, lowercase, number, and special symbol."
xpath
//XCUIElementTypeStaticText[@name="Use at least 8 characters with uppercase, lowercase, number, and special symbol."]
Attribute	

27 th step 
clike ,clear,send key (Human@2026). IN EP-FIELD
in the xpath 
name == "Enter Password"
xpath
//XCUIElementTypeOther[@name="Enter Password"]

28th step 
clike ,clear,send key (Human@2026) IN CP-FIELD
in the xpath 
name == "Confirm Password"
xpath
//XCUIElementTypeOther[@name="Confirm Password"]

29th step 
clike reset password button 
name == "RESET PASSWORD" AND label == "RESET PASSWORD" AND type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton[@name="RESET PASSWORD"]

30th step ,
verify the you sucessfuly come in signin password
name == "SIGN IN"
xpath
//XCUIElementTypeStaticText[@name="SIGN IN"]


in the every steps validation time xpath is not there using name to verify ,validate ,tab ,also work ,
xpath is note the using name .