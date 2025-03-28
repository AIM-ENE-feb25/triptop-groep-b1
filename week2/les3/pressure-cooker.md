# Pressure Cooker Exercise - External Service Integration

## Step 1: Components and Responsibilities (20 min)

### Component List
1. **ExternalServiceGateway**
   - Responsible for: Central gateway for all external communication
   - Encapsulates: HTTP communication, error handling, retry logic
   - Reason: Single point of control for external communication

2. **ExternalServiceOrchestrator**
   - Responsible for: Coordination between different external services
   - Encapsulates: Service orchestration logic, result aggregation
   - Reason: Separation of orchestration and communication

3. **SecurityManager**
   - Responsible for: API credential management
   - Encapsulates: Credential storage, key rotation
   - Reason: Central security responsibility

4. **TripAdvisorAdapter**
   - Responsible for: TripAdvisor-specific integration
   - Encapsulates: TripAdvisor data mapping
   - Reason: Isolation of service-specific logic

5. **BookingAdapter**
   - Responsible for: Booking.com-specific integration
   - Encapsulates: Booking.com data mapping
   - Reason: Isolation of service-specific logic 