# Pressure Cooker Stap 2: Component Interfaces

## Ontwerpvraag
"Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?"

## Interfaces met Methoden, Parameters en Returnwaarden

### 1. IExternalService
```java
public interface IExternalService {
    // Verbinding maken met de externe service
    boolean connect();
    
    // Uitvoeren van een verzoek op de externe service
    ServiceResponse executeRequest(ServiceRequest request);
    
    // Foutafhandeling voor de externe service
    void handleError(ServiceError error);
    
    // Verbinding verbreken met de externe service
    void disconnect();
    
    // Huidige status van de verbinding opvragen
    ConnectionStatus getConnectionStatus();
}
```

### 2. IServiceGateway
```java
public interface IServiceGateway {
    // Registreren van een service adapter
    void registerAdapter(String serviceId, IExternalService adapter);
    
    // Verwijderen van een service adapter
    void unregisterAdapter(String serviceId);
    
    // Ophalen van een service adapter
    IExternalService getAdapter(String serviceId);
    
    // Uitvoeren van een verzoek via de juiste adapter
    ServiceResponse executeServiceRequest(String serviceId, ServiceRequest request);
    
    // Lijst van beschikbare services opvragen
    List<String> getAvailableServices();
    
    // Controleert of een service beschikbaar is
    boolean isServiceAvailable(String serviceId);
}
```

### 3. IServiceAdapterFactory
```java
public interface IServiceAdapterFactory {
    // Creëren van een service adapter op basis van service type
    IExternalService createAdapter(String serviceType);
    
    // Creëren van een adapter met specifieke configuratie
    IExternalService createAdapter(String serviceType, ServiceConfiguration config);
    
    // Ophalen van de beschikbare adapter types
    List<String> getAvailableAdapterTypes();
}
```

### 4. IServiceAdapterRegistry
```java
public interface IServiceAdapterRegistry {
    // Registreren van een adapter in het register
    void registerAdapter(String serviceId, IExternalService adapter);
    
    // Ophalen van een adapter uit het register
    IExternalService getAdapter(String serviceId);
    
    // Verwijderen van een adapter uit het register
    boolean removeAdapter(String serviceId);
    
    // Controleren of een adapter bestaat
    boolean hasAdapter(String serviceId);
    
    // Ophalen van alle geregistreerde adapters
    Map<String, IExternalService> getAllAdapters();
}
```

### 5. IServiceRequestMapper
```java
public interface IServiceRequestMapper {
    // Vertalen van een generiek verzoek naar een service-specifiek verzoek
    Object mapRequest(ServiceRequest request, String serviceId);
    
    // Registreren van een custom mapper voor een specifieke service
    void registerCustomMapper(String serviceId, RequestMapperFunction mapper);
    
    // Verwijderen van een custom mapper
    void removeCustomMapper(String serviceId);
}
```

### 6. IServiceResponseMapper
```java
public interface IServiceResponseMapper {
    // Vertalen van een service-specifiek antwoord naar een generiek antwoord
    ServiceResponse mapResponse(Object rawResponse, String serviceId);
    
    // Registreren van een custom mapper voor een specifieke service
    void registerCustomMapper(String serviceId, ResponseMapperFunction mapper);
    
    // Verwijderen van een custom mapper
    void removeCustomMapper(String serviceId);
}
```

### 7. IServiceHealthMonitor
```java
public interface IServiceHealthMonitor {
    // Start monitoring van een service
    void startMonitoring(String serviceId, IExternalService service);
    
    // Stop monitoring van een service
    void stopMonitoring(String serviceId);
    
    // Huidige status van een service opvragen
    ServiceHealth getServiceHealth(String serviceId);
    
    // Registreren voor notificaties bij veranderingen in service status
    void registerHealthListener(ServiceHealthListener listener);
    
    // Verwijderen van een listener
    void removeHealthListener(ServiceHealthListener listener);
    
    // Uitvoeren van een health check
    void checkHealth(String serviceId);
}
```

### 8. IServiceConfigurationManager
```java
public interface IServiceConfigurationManager {
    // Ophalen van configuratie voor een service
    ServiceConfiguration getConfiguration(String serviceId);
    
    // Opslaan of bijwerken van configuratie voor een service
    void saveConfiguration(String serviceId, ServiceConfiguration config);
    
    // Verwijderen van configuratie
    void removeConfiguration(String serviceId);
    
    // Laden van configuratie uit externe bron (bestand, database, etc.)
    void loadConfigurations(String source);
    
    // Opslaan van alle configuraties naar externe bron
    void saveConfigurations(String destination);
}
```

## Ondersteunende Datatypes

### ServiceRequest
```java
public class ServiceRequest {
    private String operation;
    private Map<String, Object> parameters;
    private RequestPriority priority;
    
    // Constructors, getters en setters
}
```

### ServiceResponse
```java
public class ServiceResponse {
    private boolean success;
    private Object data;
    private List<ServiceError> errors;
    
    // Constructors, getters en setters
}
```

### ServiceError
```java
public class ServiceError {
    private String code;
    private String message;
    private ErrorSeverity severity;
    
    // Constructors, getters en setters
}
```

### ServiceConfiguration
```java
public class ServiceConfiguration {
    private String endpoint;
    private Map<String, String> credentials;
    private int timeout;
    private RetryPolicy retryPolicy;
    
    // Constructors, getters en setters
}
```

### ConnectionStatus
```java
public enum ConnectionStatus {
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    FAILED
}
```

### ServiceHealth
```java
public class ServiceHealth {
    private String serviceId;
    private boolean available;
    private double responseTime;
    private Date lastChecked;
    
    // Constructors, getters en setters
}
```

## Toelichting Interface Design

Deze interfaces zijn ontworpen volgens het principe "Program to an interface, not an implementation" met de volgende voordelen:

1. **Flexibiliteit**: Door te programmeren tegen interfaces kunnen implementaties eenvoudig worden vervangen.
2. **Testbaarheid**: Het is gemakkelijk om mock implementaties te maken voor tests.
3. **Duidelijk Contract**: Elke interface definieert een duidelijk contract dat implementaties moeten volgen.
4. **Loose Coupling**: De componenten zijn alleen afhankelijk van interfaces, niet van concrete implementaties.
5. **Uitbreidbaarheid**: Nieuwe functionaliteit kan worden toegevoegd door nieuwe methoden aan interfaces toe te voegen of door interfaces te laten overerven. 