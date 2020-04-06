# Overview

Camel redelivery for transacted routes which tries to do a trx rollback and subsequent redelivery of the message. 
This is helpful if you do not have a backing system like JMS to do the redelivery of the message.

