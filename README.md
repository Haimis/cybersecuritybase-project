LINK: [https://github.com/Haimis/cybersecuritybase-project](https://github.com/Haimis/cybersecuritybase-project)

This course project is a simple web application with multiple security flaws. Web application let's you to create account, 
sign in and add notes. To install the application clone the reposition and use in IDE (like netBeans) or generate .jar file 
and run from command line. Default port for web application is http://localhost:8080. 

FLAW 1:

## Broken Authentication

The first security flaw regards to broken authentication flaws. The application does not limit the amount of sign in attempts 
at all. So it's very vulnerable to brute forze fuzzing attacks. Especially because there's no conditions what kind of passwords 
user should be using.

To fix the flaw a limit for sign in attempts should be adapted via existing library or by hand. Also the password strength 
should be somehow monitored during registration. In general broken authentication flaws can be fought with following 
best practicies: 1. Multi factor authentication where user is authenticated not just by what they know (password) but also 
with something they have (fingerprint, or verification code to mobilephone), 2. Checking passwords and their complexity. 
Password security can be raisen proactively in registration phase by declining most well known passwords and requiring 
complex enough passwords with upper and lower case letters, numbers and other carachters.

FLAW 2:

## Sensitive Data Exposure

The second security flaw regards sensitive data exposure. While studying the code one will notice that passwords are not 
encrypted at all but saved in clear text format.

To fix the flaw password encryption should be used. Some components to do this can be found from config package. A good rule 
of thumb is to use some widely used third party encryption algorithms. It's also a good idea to not store sensitive data if 
you don't really need that data for application to perform it's task.

FLAW 3:

## Broken Access Control

The third security flaw regards broken access control. When signed in to the application a logout button is provided but it
won't end your session for real. If you press the button and then navigate to "/notes" you will still be able to see the 
notes of previous user.

To fix the flaw a proper session management should be introduced. One option would be to uncomment line 58 in NoteController 
to enable sessionStatus controlling. I'm sure there would be more sophisticated and secure manners for session control 
available than the one used here. At least Owasp ZAP found a Cookie Without SameSite Attribute,  which means that the cookie 
can be sent as a result of a 'cross-site' request. This will open vulnerabilities to  cross-site script attacks so better 
session control should be adapted. A good general approach to broken access control is to deny everything by default so 
nothing will be visible to user unless explicitly allowed. Good logging of failures and manual testing are also important 
measures to fight broken access control flaws.

FLAW 4:

## Using Components with Known Vulnerabilities

The fourth security flaw regards using components with known vulnerabilities. A dependency check will reveal that 14 
dependencies would have 86 vulnerabilities in total.

To fix this there's no simple solution. Dependencies are maintained by third parties and they fix the flaws at their own pace.
As a rule of thumb three points should be considered while using dependencies: 1. Use well known, well maintained libraries 
from trusted sources. 2. Always use the latest stable version. 3. Don't enable dependencies you don't really need in your 
application. 4. Constantly monitor for security reports and new patches on your dependencies. This can be done by following 
CVE (Common Vulnerabilities and Exposures) database.

FLAW 5:

## Insufficient Logging & Monitoring

The fifth security flaw regards insufficient logging and monitoring. The application doesn't provide any logging or monitoring
at all. This means administrator has no tools to detect possible security breaches or breach attempts. 

To fix this some kind of logging and monitoring should be provided. At least login failures, access control failures and 
server side validation failures should be logged. I assume best option would be to use some third party micro service. Using 
off the shell option should assure that alerts are automated, log content is sufficient, it's in proper and easy to use format 
and some kind of integrity control is enabled. This would save time as DevOps culture will bring more monitoring 
responsibilities to developers who might be working with a completely different project at the time. It's also important to 
have plan how to response to data breaches.

## Bonus

More flaws can be explored with Owasp zap. At least possible weaknesses regarding SQL Injection and Cross site Scripting can 
be found. Also a potential Buffer Overflow error is detected. Buffer Overflow exploit is a situation where piece of memory 
with certain length is overwritten with longer string and this would cause overwriting of later memory blocks and malfunctioning 
of application. Buffer Overflow was present in Owasp Top 10 2004 edition. To prevent Buffer Overflow errors it's important to 
constantly monitor your infrastructure and in case of bugs or exploits re-evaluate parts of the code where user input is 
received.

