# Production implementation checklist

- Replace in-memory Maps with PostgreSQL.
- Add verified identity/authentication and server-side authorization.
- Add invitation token hashing, expiry, revocation, one-time use and audit trail.
- Add admin approval endpoint; never trust client-supplied role.
- Add LiveKit room creation and server-generated short-lived participant tokens.
- Implement floor-control/active-speaker lock so only one PTT speaker transmits at a time.
- Add moderation, block/report, rate limiting, abuse detection and privacy policy.
- Add FCM push notifications for invitations and incoming channel events.
- Load test 1,000 listeners per room and define regional deployment, bandwidth and cost limits.
- Implement Android foreground microphone service, audio focus, Bluetooth routing and permission UX.
- Complete security review, privacy/data-safety declarations and Play policy checks.
