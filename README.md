# Cabzza

## Running project on Tomcat server
To run the project you can use embedded Tomcat Web Application container. It is handled by the Tomcat Maven Plugin (which is configured in the _pom.xml_ file). All you need to do is to run `mvn clean tomcat7:run` command from the base directory (you have to install _Maven_ first). Ypu can checkout deployed website typing _localhost:8080_ address in your browser.
*Warning*
This way of building and deploying application is a subject to change. We will use standalone Tomcat server in the future

## Bootstrap and SASS
The _/src/main/cabzzaTheme_ directory contains bootstrap theme for the project in SASS format. The configuration is stored in the _config.rb_ file. Default css output path is _/src/main/webapp/resources/css/_
The scss files can be compiled on the fly using compass. To use that feature you need to install following applications:
* ruby
* compass
* gem bootstrap-sass (using gem install command)

*Usage*
Run `compass watch` command int the directory that contains _config.rb_ file. As you can see this runs in a infinte loop. To run this command in the background type `compass watch &` command. Whenever changes are being applied to files in the _scss_ directory, _compass_ compiles and replaces them with stale styles in the _webapp/resources/css_ directory thus you can see changes on the website without restarting Tomcat.

## Technologies used
* Thymeleaf templating engine (_html_ files)
* Spring MVC framewrok
* Spring Security
* SASS
* logback (for logging urposes, will be changed to slf4j in the future)
* Hibernate (used for stroing users)
* HSQLDB driver


#### This readme file is a subject to change
