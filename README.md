# StAXON - JSON via StAX

[StAXON](http://beckchr.github.com/staxon/) lets you read and write JSON using the Java Streaming API for XML (`javax.xml.stream`).

More specifically, StAXON provides implementations of the

- StAX Cursor API (`XMLStreamReader` and `XMLStreamWriter`)
- StAX Event API (`XMLEventReader` and `XMLEventWriter`)

for JSON.

The availability of a StAX implementation acts as a door opener for JSON to powerful XML related technologies like

- XSL transformations (XSLT)
- XML binding API (JAXB)
- XML Schema Definition (XSD)

## Main Features

- Supports [Jackson](http://jackson.codehaus.org/) and [Gson](http://code.google.com/p/google-gson/) as JSON backends
- Full XML namespace support
- Start JSON arrays via XML processing instruction
- Memory efficient, even for very large documents
- It's pretty fast ([benchmark](https://github.com/beckchr/staxon/wiki/Benchmark))…

The XML-to-JSON [Mapping Convention](https://github.com/beckchr/staxon/wiki/Mapping-Convention) used by StAXON is
similar to the [Badgerfish](http://www.sklar.com/badgerfish/) convention but attempts to avoid needless text-only
JSON objects to generate a more compact JSON.

## Basic Usage

If you know StAX, you'll notice that there's little new: just obtain a reader or writer
from StAXON and you're ready to go.

### Writing JSON

Create a JSON-based writer:

	XMLStreamWriter writer = new JsonXMLOutputFactory().createXMLStreamWriter(System.out);

Write your document:

	writer.writeStartDocument();
	writer.writeStartElement("alice");
	writer.writeCharacters("charlie");
	writer.writeEndElement();
	writer.writeEndDocument();
	writer.close();

With an XML-based writer, this would have produced something like

	<?xml version="1.0"?>
	<alice>charlie</alice>

However, with our JSON-based writer, the output is

	{"alice":"charlie"}

### Reading JSON

Create a JSON-based reader:

	StringReader json = new StringReader("{\"alice\":\"charlie\"}");
	XMLStreamReader reader = new JsonXMLInputFactory().createXMLStreamReader(json);

Read your document:

	assert reader.getEventType() == XMLStreamConstants.START_DOCUMENT;
	reader.nextTag(); 
	assert reader.isStartElement() && "alice".equals(reader.getLocalName());
	reader.next();
	assert reader.hasText() && "charlie".equals(reader.getText());
	reader.nextTag();
	assert reader.isEndElement();
	reader.next();
	assert reader.getEventType() == XMLStreamConstants.END_DOCUMENT;
	reader.close();

## Documentation

[StAXON Wiki](https://github.com/beckchr/staxon/wiki/)

## Download

You can get StAXON artifacts from our [maven repositories](http://beckchr.github.com/staxon/maven/) at github: 

	<repositories>
		<repository>
			<id>staxon</id>
			<url>http://beckchr.github.com/staxon/maven/releases</url>
		</repository>
	</repositories>

	<dependencies>
		<dependency>
			<groupId>de.odysseus.staxon</groupId>
			<artifactId>staxon</artifactId>
			<version>0.6.1</version>
		</dependency>
		<dependency>
			<groupId>org.codehaus.jackson</groupId>
			<artifactId>jackson-core-asl</artifactId>
			<version>1.8.5</version>
		</dependency>
	</dependencies>

Manually download the latest release: StAXON 0.6.1 (2011/09/17):

- [staxon-0.6.1.jar](http://beckchr.github.com/staxon/maven/releases/de/odysseus/staxon/staxon/0.6.1/staxon-0.6.1.jar)
- [staxon-0.6.1-sources.jar](http://beckchr.github.com/staxon/maven/releases/de/odysseus/staxon/staxon/0.6.1/staxon-0.6.1-sources.jar)

Make sure you have the `jackson-core` (or `gson`) jar on your classpath ([jackson download](http://wiki.fasterxml.com/JacksonDownload)).

## Development

[Github project](http://github.com/beckchr/staxon/)

## License

StAXON is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).


_(c) 2011 Odysseus Software_