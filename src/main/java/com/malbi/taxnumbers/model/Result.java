//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11-b140731.1112
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.22 at 03:55:09 PM EET
//

package com.malbi.taxnumbers.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="taxinvoicenumber">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * http://wiki.eclipse.org/EclipseLink/Examples/MOXy/JAXB/Compiler
 * http://sourceforge.net/projects/jaxb-builder/?source=typ_redirect
 * http://stackoverflow.com/questions/13346101/generate-pojos-without-an-xsd
 *
 * There is also an official doc
 * http://docs.oracle.com/javaee/7/tutorial/jaxrs-advanced007.htm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "taxinvoicenumber" })
@XmlRootElement(name = "result")
public class Result {

	@XmlElement(required = true)
	protected Result.Taxinvoicenumber taxinvoicenumber;

	/**
	 * Gets the value of the taxinvoicenumber property.
	 *
	 * @return possible object is {@link Result.Taxinvoicenumber }
	 *
	 */
	public Result.Taxinvoicenumber getTaxinvoicenumber() {
		return taxinvoicenumber;
	}

	/**
	 * Sets the value of the taxinvoicenumber property.
	 *
	 * @param value
	 *            allowed object is {@link Result.Taxinvoicenumber }
	 *
	 */
	public void setTaxinvoicenumber(Result.Taxinvoicenumber value) {
		this.taxinvoicenumber = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.taxinvoicenumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Result other = (Result) obj;

		return Objects.equals(this.taxinvoicenumber, other.taxinvoicenumber);
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 *
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;simpleContent>
	 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
	 *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/extension>
	 *   &lt;/simpleContent>
	 * &lt;/complexType>
	 * </pre>
	 *
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "value" })
	public static class Taxinvoicenumber {

		@XmlValue
		protected String value;
		@XmlAttribute(name = "number")
		protected String number;

		/**
		 * Gets the value of the value property.
		 *
		 * @return possible object is {@link String }
		 *
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the value property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 *
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Gets the value of the number property.
		 *
		 * @return possible object is {@link String }
		 *
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * Sets the value of the number property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 *
		 */
		public void setNumber(String value) {
			this.number = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.number);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Taxinvoicenumber other = (Taxinvoicenumber) obj;
			return Objects.equals(this.number, other.number);
		}

	}

}
