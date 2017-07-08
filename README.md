WHAT THE APPLICATION DOES
=========================
I've settled down to create a tracker that per country counts the number of currency from&to received, 
showing more popular "pairs".

Few notes:
- for the sake of simpler UI a limited number of countries and currencies are supported.
    they are configures in the application.yml
- country and currency are validated in the Consumer or the message will be rejected.
  

DESCRIPTION OF THE APPROACH
===========================
I've settled on creating a single spring-boot application, deployed as a single war.
The application is modular and the decoupling points are implemented by interfaces so that different implementations
might be swapped, to allow for distributing the three main components (MessageConsumer, MessageProcessor and UI) on
three different services.



