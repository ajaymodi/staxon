/*
 * Copyright 2011 Odysseus Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.odysseus.staxon;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import de.odysseus.staxon.event.SimpleXMLEventWriter;

/**
 * Abstract XML output factory.
 */
public abstract class AbstractXMLOutputFactory extends XMLOutputFactory {
	@Override
	public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
		try {
			return createXMLStreamWriter(new OutputStreamWriter(stream, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new XMLStreamException(e);
		}
	}

	@Override
	public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
		if (result instanceof StreamResult) {
			StreamResult streamResult = (StreamResult) result;
			OutputStream output = streamResult.getOutputStream();
			if (output != null) {
				return createXMLStreamWriter(output);
			}
			Writer writer = streamResult.getWriter();
			if (writer != null) {
				return createXMLStreamWriter(writer);
			}
			if (result.getSystemId() != null) {
				throw new XMLStreamException("Cannot open system id as URL for writing: " + result.getSystemId());
			} else {
				throw new XMLStreamException("Invalid stream result: none of output, writer, systemId set");
			}
		}
		throw new XMLStreamException("Unsupported result type: " + result.getClass());
	}

	@Override
	public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
		return createXMLEventWriter(createXMLStreamWriter(result));
	}

	@Override
	public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
		return createXMLEventWriter(createXMLStreamWriter(stream));
	}

	@Override
	public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding) throws XMLStreamException {
		return createXMLEventWriter(createXMLStreamWriter(stream, encoding));
	}

	@Override
	public XMLEventWriter createXMLEventWriter(Writer stream) throws XMLStreamException {
		return createXMLEventWriter(createXMLStreamWriter(stream));
	}

	public XMLEventWriter createXMLEventWriter(XMLStreamWriter writer) throws XMLStreamException {
		return new SimpleXMLEventWriter(writer);
	}
}