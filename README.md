# Cyber Human iOS Automation Project Start

# 2nd commit
1.Verify Home Page
2.Click Wellbeing Dashboard
was same code same xpath dont chnage 

3.Click AB CHOPRA HOUSE
name == "AB CHOPRA HOUSE"
xpath
//XCUIElementTypeStaticText[@name="AB CHOPRA HOUSE"]

4.Click DISCOVER
name == "DISCOVER"
xpath
//XCUIElementTypeStaticText[@name="DISCOVER"]

5.Verify Discover Page is Shown
name == "DISCOVER +"
xpath
//XCUIElementTypeStaticText[@name="DISCOVER +"]

6.Swipe Up Once
[{"key":"elementId","value":"35000000-0000-0000-C940-000000000000","name":"elementId"},{"key":"type","value":"XCUIElementTypeScrollView","name":"type"},{"key":"enabled","value":"true","name":"enabled"},{"key":"visible","value":"true","name":"visible"},{"key":"accessible","value":"true","name":"accessible"},{"key":"x","value":"24","name":"x"},{"key":"y","value":"135","name":"y"},{"key":"width","value":"342","name":"width"},{"key":"height","value":"685","name":"height"},{"key":"index","value":"0","name":"index"},{"key":"traits","value":"","name":"traits"}]

7.Swipe Down Once
(Same as above.)

8.Click DISCOVER +
name == "DISCOVER +"
xpath
//XCUIElementTypeStaticText[@name="DISCOVER +"]

9.Click FILTER
name == "FILTER"
xpath
//XCUIElementTypeButton[@name="FILTER"]

10.Click Mind & Emotions Radio Button
name == "Mind & Emotions"
xpath
//XCUIElementTypeStaticText[@name="Mind & Emotions"]

11.Set Timing
value == "the end value is 60.0"
xpath
//XCUIElementTypeOther[@value="the end value is 60.0"]

12.Click APPLY
name == "APPLY"
xpath
//XCUIElementTypeButton[@name="APPLY"]

13.Click See All 1,
(//XCUIElementTypeButton[@name="See All"])[1]
 Verify Listen Page,
 name == "Listen"
xpath
//XCUIElementTypeStaticText[@name="Listen"]
  Click Back
  type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton
Attribute	Value
elementId
07010000-0000-0000-C940-000000000000

14.Click See All 3, 
**/XCUIElementTypeButton[`name == "See All"`][2]
xpath
(//XCUIElementTypeButton[@name="See All"])[2]
Verify Watch Page,
name == "Watch"
xpath
//XCUIElementTypeStaticText[@name="Watch"]
Click Back
type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton
Attribute	Value
elementId
5A010000-0000-0000-C940-0000000

15.Swipe Up on Container
**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeScrollView
xpath
//XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeScrollView
Attribute	Value
elementId
7E010000-0000-0000-C940-000000000000
type
XCUIElementTypeScrollView

16.Click See All 2,
**/XCUIElementTypeButton[`name == "See All"`][3]
xpath
(//XCUIElementTypeButton[@name="See All"])[3]
Attribute	Value
elementId
86010000-0000-0000-C940-00000000000
Verify Read Page, 
name == "Read"
xpath
//XCUIElementTypeStaticText[@name="Read"]
Click Back
type == "XCUIElementTypeButton"
xpath
//XCUIElementTypeButton
Attribute	Value
elementId
B3010000-0000-0000-C940-000000000

17.Click Video Item
[{"key":"elementId","value":"F7010000-0000-0000-C940-000000000000","name":"elementId"},{"key":"type","value":"XCUIElementTypeOther","name":"type"},{"key":"enabled","value":"true","name":"enabled"},{"key":"visible","value":"true","name":"visible"},{"key":"accessible","value":"true","name":"accessible"},{"key":"x","value":"24","name":"x"},{"key":"y","value":"600","name":"y"},{"key":"width","value":"25","name":"width"},{"key":"height","value":"25","name":"height"},{"key":"index","value":"1","name":"index"},{"key":"traits","value":"","name":"traits"}]

18.Click Search Box and Search "one"
name == "Search"
xpath
//XCUIElementTypeTextField[@name="Search"]


19.Verify "One" is Shown in Search Results
skip the step

20.Click New File Icon
name == "New File"
xpath
//XCUIElementTypeButton[@name="New File"]

21.Create File by Typing "New"
name == "Enter file name"
xpath
//XCUIElementTypeTextField[@name="Enter file name"]

22.Trigger Save Before Close
skip the step 

23.Click Close Icon
type == "XCUIElementTypeImage"
xpath
//XCUIElementTypeImage
Attribute	Value
elementId
07020000-0000-0000-C940-000000000000

24.Verify Saved Dialog is Displayed
name == "SAVED"
xpath
//XCUIElementTypeStaticText[@name="SAVED"]

25.Get Success Message
name == "Your article has been successfully saved."
xpath
//XCUIElementTypeStaticText[@name="Your article has been successfully saved."]

26.Click OK Button
name == "OK"
xpath
//XCUIElementTypeButton[@name="OK"]


27.Click Archive
name == "ARCHIVE"
xpath
//XCUIElementTypeStaticText[@name="ARCHIVE"]

28.Verify Archive Page is Displayed
name == "ARCHIVE"
xpath
//XCUIElementTypeStaticText[@name="ARCHIVE"]

29.Verify New File is in Archive
[{"key":"elementId","value":"AD020000-0000-0000-C940-000000000000","name":"elementId"},{"key":"type","value":"XCUIElementTypeImage","name":"type"},{"key":"name","value":"New\\nModified Jan 22","name":"name"},{"key":"label","value":"New\\nModified Jan 22","name":"label"},{"key":"enabled","value":"true","name":"enabled"},{"key":"visible","value":"true","name":"visible"},{"key":"accessible","value":"true","name":"accessible"},{"key":"x","value":"16","name":"x"},{"key":"y","value":"168","name":"y"},{"key":"width","value":"358","name":"width"},{"key":"height","value":"171","name":"height"},{"key":"index","value":"1","name":"index"},{"key":"traits","value":"Image","name":"traits"}]

30.Click New File in Archive
name == "New Modified Jan 22"
xpath
//XCUIElementTypeImage[@name="New Modified Jan 22"]

31.Verify File is Open
[{"key":"elementId","value":"AD020000-0000-0000-C940-000000000000","name":"elementId"},{"key":"type","value":"XCUIElementTypeImage","name":"type"},{"key":"name","value":"New\\nModified Jan 22","name":"name"},{"key":"label","value":"New\\nModified Jan 22","name":"label"},{"key":"enabled","value":"true","name":"enabled"},{"key":"visible","value":"true","name":"visible"},{"key":"accessible","value":"true","name":"accessible"},{"key":"x","value":"16","name":"x"},{"key":"y","value":"168","name":"y"},{"key":"width","value":"358","name":"width"},{"key":"height","value":"171","name":"height"},{"key":"index","value":"1","name":"index"},{"key":"traits","value":"Image","name":"traits"}]

 add some new steps i give. you 

 step 31.1
 clike menu icon
 //XCUIElementTypeApplication[@name="AB Chopra"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeImage[2]
Attribute	Value
elementId
CC020000-0000-0000-C940-0000000

step 31.2 clike edit
name == "EDIT"
xpath
//XCUIElementTypeStaticText[@name="EDIT"]

step 31.3 clike enter file name 
name == "Enter File Name"
xpath
//XCUIElementTypeTextField[@name="Enter File Name"]
and type EDIT NEW

STEP 31.4 CLIKE SAVE BUTTON
name == "SAVE"
xpath
//XCUIElementTypeButton[@name="SAVE"]

STEP 31.5 VERIFY THE SUCESS DIALOG SHOW
name == "SUCCESS"
xpath
//XCUIElementTypeStaticText[@name="SUCCESS"]

STEP 31.6 GET AND SHOW THE SUCESS MESSAGE
name == "Your archive name has been successfully updated."
xpath
//XCUIElementTypeStaticText[@name="Your archive name has been successfully updated."]


STEP 31.7 CLIKE OKAY BUTTON
name == "OK"
xpath
//XCUIElementTypeButton[@name="OK"]

STEP 31.8 AGAIN CLIKE THE MENU ICON
//XCUIElementTypeApplication[@name="AB Chopra"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeImage[2]
Attribute	Value
elementId
CC020000-0000-0000-C940-0000000

STEP 31.9 CLIKE ORGANISE
name == "ORGANISE"
xpath
//XCUIElementTypeStaticText[@name="ORGANISE"]


THEN CONTINUE THE NORMAL STEPS

32.Click Remove Icon
[{"key":"elementId","value":"4C030000-0000-0000-C940-000000000000","name":"elementId"},{"key":"type","value":"XCUIElementTypeImage","name":"type"},{"key":"enabled","value":"true","name":"enabled"},{"key":"visible","value":"true","name":"visible"},{"key":"accessible","value":"true","name":"accessible"},{"key":"x","value":"40","name":"x"},{"key":"y","value":"393","name":"y"},{"key":"width","value":"21","name":"width"},{"key":"height","value":"21","name":"height"},{"key":"index","value":"1","name":"index"},{"key":"traits","value":"Image","name":"traits"}]

33.Click YES Button
name == "YES"
xpath
//XCUIElementTypeButton[@name="YES"]

34.Verify Success Dialog is Displayed
name == "SUCCESS"
xpath
//XCUIElementTypeStaticText[@name="SUCCESS"]

35.Get Delete Success Message
name == "Your article has been successfully deleted."
xpath
//XCUIElementTypeStaticText[@name="Your article has been successfully deleted."]


36.Click OK Button
name == "OK"
xpath
//XCUIElementTypeButton[@name="OK"]



PLEASE CORRECTLY REPLCASE THOS ALL XPATH AND IF XPATH IS NOT THERE FOR ANY ELEMENT USING NAME ,OR ELEMNT ID TO 
TAB OR CLIKE ,



