# Pressure Cooker Stap 3: Dynamische Diagrammen - Volgorde van Aanroepen

## Ontwerpvraag
"Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?"

## Toelichting op de Dynamische Diagrammen

Om de volgorde van aanroepen tussen de verschillende componenten duidelijk te visualiseren, hebben we drie C4 dynamische diagrammen gemaakt. Elk diagram richt zich op een specifiek scenario om de leesbaarheid te verbeteren en de interacties goed te kunnen weergeven.

### Diagram 1: Toevoegen van een nieuwe service adapter

Het eerste diagram toont de sequence wanneer een client verzoekt om een nieuwe service toe te voegen:

1. De **Core Application** vraagt eerst de juiste configuratie op via de **ServiceConfigurationManager**
2. Vervolgens wordt de **ServiceAdapterFactory** gebruikt om een nieuwe adapter te maken op basis van het service type en de configuratie
3. De nieuwe adapter wordt geregistreerd in de **ServiceAdapterRegistry**
4. De **ServiceHealthMonitor** begint met het monitoren van de gezondheid van de nieuwe service
5. Tenslotte wordt een bevestiging teruggegeven aan de client

Dit proces zorgt ervoor dat nieuwe services eenvoudig kunnen worden toegevoegd zonder wijzigingen aan bestaande code.

### Diagram 2: Uitvoeren van een verzoek naar een externe service

Het tweede diagram laat zien hoe een client-verzoek wordt afgehandeld:

1. De client stuurt een verzoek naar de **Core Application**
2. De core app delegeert het verzoek aan de **ServiceGateway**
3. De gateway haalt de juiste adapter op uit het **ServiceAdapterRegistry**
4. Het verzoek wordt vertaald naar een service-specifiek formaat via de **ServiceRequestMapper**
5. De gateway controleert de beschikbaarheid van de service via de **ServiceHealthMonitor**
6. De **TransportServiceAdapter** maakt verbinding met de externe API
7. Het verzoek wordt uitgevoerd en de response wordt verwerkt
8. De response wordt gemapt via de **ServiceResponseMapper**
9. Het resultaat wordt teruggegeven aan de client

Dit toont de loose coupling tussen componenten en hoe ze samenwerken.

### Diagram 3: Dynamisch vervangen van een service adapter

Het derde diagram toont hoe een bestaande service dynamisch kan worden vervangen:

1. De huidige adapter wordt opgehaald uit het registry
2. De monitoring wordt gestopt
3. De nieuwe configuratie wordt opgehaald
4. Een nieuwe adapter wordt gemaakt met de nieuwe configuratie
5. De oude adapter wordt verwijderd en de nieuwe wordt geregistreerd
6. Monitoring wordt gestart voor de nieuwe adapter

Dit scenario illustreert de flexibiliteit van het ontwerp en de mogelijkheid om services te updaten zonder downtime.

## C4 Model Approach

We hebben specifiek gekozen voor het C4 model om de dynamische interacties te visualiseren omdat:

1. Het een gestandaardiseerde notatie biedt die makkelijk te begrijpen is
2. Het verschillende niveaus van abstractie ondersteunt
3. Het duidelijk de relaties tussen componenten laat zien
4. Het goed aansluit bij moderne software-architecturen

## Coupling Analyse

De diagrammen laten zien hoe we low coupling hebben bereikt tussen de componenten:

1. **Loose Coupling**: Componenten communiceren via interfaces, niet via concrete implementaties
2. **Dependency Inversion**: High-level modules (Core, Gateway) zijn niet afhankelijk van low-level modules (specifieke adapters)
3. **Interface Segregation**: Elke interface heeft een specifiek doel en verantwoordelijkheid
4. **Open/Closed Principle**: Het systeem is open voor uitbreiding (nieuwe adapters) maar gesloten voor wijziging

### Voordelen van deze Volgorde van Aanroepen

1. **Modulariteit**: Componenten kunnen onafhankelijk worden ontwikkeld, getest en vervangen
2. **Flexibiliteit**: Nieuwe services kunnen dynamisch worden toegevoegd of verwijderd
3. **Onderhoudbaarheid**: Wijzigingen in één component hebben minimale impact op andere componenten
4. **Testbaarheid**: Elke component kan in isolatie worden getest
5. **Schaalbaarheid**: Door de lage koppeling kunnen componenten eenvoudig worden geschaald of gedistribueerd 