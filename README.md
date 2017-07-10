### DESCRIPTION OF THE APPLICATION

The application is made up of a couple of "submodules" (not separated into different maven modules for simplicity):
- The consumer will just receive the message, check their basic validity (i.e all fields populated properly) 
  and dispatch them to a generic `ProcessorService`
- The processor layer will instead extract some simple indicators from the messages and send them to a graphite instance.
  The graphite instance can be customized by setting the `processor.graphite.host` and `processor.graphite.port` in
  application.yml or via system properties.

I've settled down to use spring-boot as a framework to implement the code and set up a graphite+grafana UI to show the 
metrics collected.
The system will run also without a graphite/graphana server running, but won't be of much use as there is no easy way to 
see the metrics. A snapshot of the metrics is still exposed through the spring-actuator default endpoints.

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
  - a graph with the trend per second of the messages per country (graph is stacked)
  - a graph with the trend per second of the currencies sold (graph is stacked)
  - a graph with the trend per second of the currencies purchased (graph is stacked)
- there is a python simple tester which was used as an integration test. Check the file `src/test/python/torque.py`, 
  use --help to see the (limited) options to configure it. 
  The random messages generated aren't using any sophisticated algorithm.
- the current installation doesn't involve any authentication on the messaging endpoint, due to the specs of the exercise
  not mentioning anything. Proper authentication and enabling SSL is of course the first next step to make this eventually 
  production ready. I didn't enable SSL as I could only provide a self-signed certificate and this may cause some headaches 
  when writing tests.
- final, the name of the project (trabiccolo) is the only word it could come to mind to describe a non-well-identified 
  machinery that does something. :)
