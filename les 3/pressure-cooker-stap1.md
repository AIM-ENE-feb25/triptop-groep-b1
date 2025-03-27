# Pressure Cooker Stap 1: Componenten en Verantwoordelijkheden

## Ontwerpvraag
"Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?"

## Componenten met Verantwoordelijkheden

### 1. ServiceGateway
**Verantwoordelijkheid**: De centrale toegangspoort die alle verzoeken van de core applicatie ontvangt en doorstuurt naar de juiste service adapter. Beheert de registratie van service adapters en kan fallback-mechanismen bieden.

### 2. IExternalService (Interface)
**Verantwoordelijkheid**: Definieert het contract dat alle service adapters moeten implementeren, zodat de ServiceGateway op uniforme wijze met alle adapters kan communiceren.

### 3. TransportServiceAdapter
**Verantwoordelijkheid**: Vertaalt de generieke verzoeken van de ServiceGateway naar specifieke verzoeken voor de Transport API en vertaalt de antwoorden terug naar het formaat dat de core applicatie verwacht.

### 4. HotelServiceAdapter
**Verantwoordelijkheid**: Vertaalt de generieke verzoeken van de ServiceGateway naar specifieke verzoeken voor de Hotel API en vertaalt de antwoorden terug naar het formaat dat de core applicatie verwacht.

### 5. ServiceAdapterFactory
**Verantwoordelijkheid**: Creëert de juiste service adapters op basis van configuratie en zorgt ervoor dat ze correct geïnitialiseerd worden voordat ze bij de ServiceGateway geregistreerd worden.

### 6. ServiceAdapterRegistry
**Verantwoordelijkheid**: Houdt bij welke service adapters beschikbaar zijn en onder welke naam/sleutel ze benaderd kunnen worden. Maakt het mogelijk om adapters dynamisch toe te voegen of te verwijderen tijdens runtime.

### 7. ServiceRequestMapper
**Verantwoordelijkheid**: Zet generieke verzoeken vanuit de core applicatie om naar het specifieke formaat dat een service adapter verwacht.

### 8. ServiceResponseMapper
**Verantwoordelijkheid**: Zet specifieke antwoorden van een service adapter om naar een gestandaardiseerd formaat dat de core applicatie kan verwerken.

### 9. ServiceHealthMonitor
**Verantwoordelijkheid**: Bewaakt de status van alle externe services en rapporteert wanneer een service niet beschikbaar is, zodat de ServiceGateway fallback-strategieën kan activeren.

### 10. ServiceConfigurationManager
**Verantwoordelijkheid**: Beheert de configuratie van alle service adapters, zoals endpoint URLs, authenticatie-informatie en timeout-instellingen, waardoor het mogelijk is om deze instellingen centraal aan te passen zonder code te wijzigen.

## Toelichting Ontwerpprincipes

Bij het definiëren van deze componenten zijn de volgende principes toegepast:

1. **Encapsulate What Varies**: Elke service adapter encapsuleert de specifieke logica voor een externe service, waardoor wijzigingen in één service geen invloed hebben op andere services.

2. **Single Responsibility Principle**: Elke component heeft één duidelijke verantwoordelijkheid, zoals het vertalen van verzoeken of het bewaken van de servicestatus.

3. **Cohesion**: Gerelateerde functionaliteit is gegroepeerd in componenten met een hoge cohesie, zoals de mapper-componenten die zich specifiek bezighouden met dataconversie.

4. **Separation of Concerns**: De verschillende aspecten van service-integratie (routing, mapping, monitoring, configuratie) zijn gescheiden in aparte componenten. 