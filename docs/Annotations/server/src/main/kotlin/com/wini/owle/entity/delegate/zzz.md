
DaoDelegate is a type designed to make a single type responsible for all concerns related to building an EntityID for a type, extracting the EntityID's value, making inferences on the basis of how its id is identified in API request params... whatever.

*Priority 8*: This module is an absolute mess.

- Ensure all of the implementations follow a consistent, rigorous methodology. All types represented by exposed ULongEntity and the String id override type can be handled by a common Generic implementation. Each CompositeEntity has its own methodology to observe but should be approached in a complete and consistent way.
- Previous to the introduction of DaoDelegate, such functions were present on the CompositeEntity subclass's companion object -- ensure these are cleaned off it and instead found here for consistency.
- Arguably the greatest challenge we call on DaoDelegate for help with is determining whether a valid complete articulation of a CompositeID-based API-facing type can be evaluated from the request params of an ApplicationCall. See  CompositeIdEndpointImpl::compositeIdFromRequestParams and ensure proper behavior for each CompositeID-based type 