### DESCRIPTION OF THE APPLICATION

The application is made up of a couple of "submodules" (not separated into different maven modules for simplicity):
- The consumer will just receive the message, check their basic validity (i.e all fields populated properly) 
  and dispatch them to a generic `ProcessorService`
- The processor layer will instead calculate some metrics on the messages. 

I've settled down to use spring-boot as a framework to implement the code and set up a graphite+grafana UI to show the 
metrics collected.

Few important notes:
- I've purposely limited countries and currencies to a small subset (those are configured in the `application.yml`), so 
  that I could have some invalid messages posted sometimes.
- The `ProcessorService` interface decouples consumer and processor. This allows - for example - to substitute the 
  processor with a different implementation, be it something that stores on a datastore the message for later analysis
  or sends the message on a messaging broker (JMS/ActiveMQ, RabbitMQ, ZeroMQ...) so that the same message may be 
  consumed from different processors based on the business/analysis needs.
- The choice of grafana+graphite was dictated by the amount of out-of-the box features the two components allows. 
  The UI has been pre-configured with a limited number of graphs, such as:
  - the number of messages received by the consumer, including the number of invalid messages received.
  - a graph with the trend per minute of the messages per country (graph is stacked)
  - a graph with the trend per minute of the currencies sold (graph is stacked)
  - a graph with the trend per minute of the currencies purchased (graph is stacked)
- there is a python simple tester which was used as an integration test. check the file `torque.py`
- final, the name of the project (trabiccolo) is the only word it could come to mind to describe a non-well-identified 
  machinery that does something. :)


