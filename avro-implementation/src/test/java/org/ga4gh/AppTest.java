package org.ga4gh;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.ga4gh.models.Avrobject;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testAvrobject() {
		Avrobject obj = new Avrobject();
		System.out.println(obj);
	}

	public void testAvroJsonEncoder() throws IOException {
		Avrobject obj = new Avrobject();
		System.out.println(serialize(obj));
	}

	public void testAvroByteEncoder() throws IOException {
		Avrobject obj = new Avrobject();
		serializeToByteArray(obj);
	}

	public void testAvroJsonEncoderOutput() throws IOException {
		Avrobject obj = new Avrobject();
		obj.setAvroString("test");
		System.out.println(serialize(obj));
	}
	
	public void testAvroJsonDecoder() throws IOException {
		String json = "{\"avroInt\": null, \"avroIntTwo\": 0, \"avroDouble\": 0.0, \"avroBoolean\": false, \"avroString\": \"hello\", \"avroStringTwo\": null, \"customObject\": null}";
		System.out.println(deserialize(json));
	}

	public void testAvroJsonDecoderWithMissingFields() throws IOException {
		String json = "{\"avroIntTwo\": 0, \"avroDouble\": 0.0, \"avroBoolean\": false, \"avroString\": \"hello\", \"avroStringTwo\": null, \"customObject\": null}";
		System.out.println(deserialize(json));
	}

	public String serialize(Avrobject item) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SpecificDatumWriter<Avrobject> datumWriter = new SpecificDatumWriter<Avrobject>(
				item.getSchema());
		JsonEncoder encoder = EncoderFactory.get().jsonEncoder(
				item.getSchema(), out, true);
		datumWriter.write(item, encoder);
		encoder.flush();
		out.close();
		return out.toString();
	}

	public byte[] serializeToByteArray(Avrobject item) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		DatumWriter<Avrobject> writer = new SpecificDatumWriter<Avrobject>(
				Avrobject.getClassSchema());

		writer.write(item, encoder);
		encoder.flush();
		out.close();
		return out.toByteArray();
	}

	public Avrobject deserialize(String input) throws IOException {
		SpecificDatumReader<Avrobject> datumReader = new SpecificDatumReader<Avrobject>(
				Avrobject.getClassSchema());
		JsonDecoder decoder = DecoderFactory.get().jsonDecoder(
				Avrobject.getClassSchema(), input);
		return datumReader.read(null, decoder);
	}
}
