persistent-collection
=====================

Usage:

	java
    	Map<String, Persistent> map = new PersistentMapBuilder<>()
    	  .externalizeUsing(Externalizers.serializable())
    	  .persistOn(Persistences.fileSystem("directory"))
    	  .build();

Persistent Map with configurable:
- Persistence: persist to anywhere.
- Externalization: convert values to/from bytes.

Available Persistences:
- None: no persistence, 
- File system: store entries on disk.

Available Externalizers:
- Java Serialization.
