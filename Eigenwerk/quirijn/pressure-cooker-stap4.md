# Pressure Cooker Stap 4: Class Diagram

## Ontwerpvraag
"Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?"

## Toelichting op het Class Diagram

Dit class diagram toont de essentiële klassen en interfaces die nodig zijn om de ontwerpvraag te beantwoorden. Het diagram is een uitwerking van de componenten die we in de eerdere stappen hebben geïdentificeerd.

### Kernklassen en hun Verantwoordelijkheden

1. **ServiceClient**
   - Biedt een gebruiksvriendelijke interface voor het gebruik van externe services
   - Maakt het toevoegen van nieuwe services mogelijk via de `addExternalService()` methode

2. **IServiceGateway & ServiceGateway**
   - Fungeert als centrale toegangspoort voor alle externe services
   - Beheert de registratie van service adapters
   - Delegeert verzoeken naar de juiste adapter

3. **IExternalService & BaseServiceAdapter**
   - Definieert het contract dat alle service adapters moeten implementeren
   - Biedt gemeenschappelijke functionaliteit via de abstracte basisklasse

4. **Concrete Adapters** (TransportServiceAdapter, HotelServiceAdapter, NewServiceAdapter)
   - Implementeren service-specifieke logica
   - Vertalen generieke verzoeken naar service-specifieke API-calls

### Toepassing van Ontwerpprincipes

#### Open/Closed Principle
Het systeem is open voor uitbreiding (nieuwe adapters) maar gesloten voor wijziging:
- Nieuwe service adapters kunnen worden toegevoegd zonder wijzigingen aan bestaande code
- De BaseServiceAdapter biedt een gemeenschappelijke basis voor alle adapters

#### Single Responsibility Principle
- Elke klasse heeft één duidelijke verantwoordelijkheid
- De Gateway routeert verzoeken, de Adapters implementeren service-specifieke logica

#### Dependency Inversion Principle
- High-level modules (ServiceClient, ServiceGateway) zijn niet afhankelijk van concrete implementaties
- Er wordt geprogrammeerd tegen interfaces (IServiceGateway, IExternalService)

### Proces voor het Toevoegen van een Nieuwe Service

Het toevoegen van een nieuwe externe service is eenvoudig en vereist slechts drie stappen:

1. **Maak een nieuwe adapter**: Creëer een klasse die BaseServiceAdapter uitbreidt en implementeer de service-specifieke logica

2. **Registreer de adapter**: Registreer de nieuwe adapter in de ServiceGateway

3. **Gebruik de client**: Voeg een nieuwe service toe via de ServiceClient

Dit proces vereist geen wijzigingen aan bestaande code - alleen het toevoegen van nieuwe code, wat het Open/Closed Principle illustreert.

### Conclusie

Dit class diagram toont een elegante en flexibele architectuur die het toevoegen van nieuwe externe services eenvoudig maakt. Door gebruik te maken van het Adapter patroon en het Open/Closed Principle, kunnen we nieuwe services toevoegen zonder bestaande code te wijzigen. 

De architectuur biedt een duidelijk en direct antwoord op de ontwerpvraag: "Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?" 