Author: deepak roy
Dated: 04-11-2018




While deploying birt project the structure should be as follows:

Birt Web App (default)
-------------
        logs
        report*
        scriptlib
        webcontent
        WEB-INF
        cancleTask.jsp
        index.jsp


 report*:
All reports and their supporting libraries need to be deployed under report folder

e.g.:
      report
      -------------
       	reports (container of all rptdesign files)/work location of eclipse report project directory
	res*
	temp

res*:
All supporting libraries need to be deployed under this folder (if you want to set different folder then change the path from web.xml)

	res
      ------------------
                 i18n
                 libraries
                 images
                 style
                 _template
	    .
	    .
	    .
	    .


