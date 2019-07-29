# Sources for the Excel vendor form example article
This project is the Source Code of Turning an Excel Vendor Form Into a Web Workflow article

Other dev-ref applications can be found in the [Keikai Developer Reference](https://doc.keikai.io/dev-ref)

The document introduces you how to develop a web application with Keikai through examples.
This project contains the source code for the vendor form data collection.
Please note that this is a simplified project for demonstration purposes. A production implementation should consider security and authentication aspects that are not included in this presentation.

# For New Comers
If you are new to Keikai, we recommend you to read [Tutorial](https://doc.keikai.io/tutorial) first to know some basic ideas.

# How to run

`mvn jetty:run` (requires Maven installed and a keikai server running)
keikai server is available at https://keikai.io/download
update the keikai properties file to target the local keikai server:
https://github.com/keikai/dev-ref-vendor/blob/master/src/main/resources/keikai.properties

Then visit http://localhost:8080/dev-ref-vendor/manager/

# Welcome to our sites:
## [Website](https://keikai.io)  
## [Demo](https://keikai.io/demo)
## [Document](https://doc.keikai.io)
## [Blog](https://keikai.io/blog)
