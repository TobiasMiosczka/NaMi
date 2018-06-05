package com.github.tobiasmiosczka.nami.applicationforms;

import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.enums.NamiBaustein;
import nami.connector.namitypes.NamiSchulungenMap;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import java.util.Date;
import java.util.List;

import static com.github.tobiasmiosczka.nami.applicationforms.TimeHelp.calcAge;
import static com.github.tobiasmiosczka.nami.applicationforms.TimeHelp.getDateYearString;


public class WriterApplicationDioezeseMuensterGroupLeader extends AbstractTextDocumentWriter {

	private static final Font font = new Font("Calibri", StyleTypeDefinitions.FontStyle.REGULAR, 10, Color.BLACK);
	private static final double height = 0.7;

	private final boolean keinDatum;
	private final Date datum;
	private final List<NamiSchulungenMap> schulungen;

	public WriterApplicationDioezeseMuensterGroupLeader(List<NamiSchulungenMap> schulungen, boolean keinDatum, Date datum) {
		super();
		this.schulungen = schulungen;
		this.keinDatum = keinDatum;
		this.datum = datum;
	}

	@Override
	public void manipulateDoc(List<NamiMitglied> participants, TextDocument odtDoc){
		//participants data
		Table tParticipants = odtDoc.getTableList().get(0);
		boolean first = true;
		for(int i = 0; i < participants.size(); i++){
			Row row;
			if (first) {
				row = tParticipants.getRowByIndex(4);
				first = false;
			} else {
				row = tParticipants.appendRow();
			}
			//set row style
			row.setHeight(height, true);
			for(int cell = 0; cell < row.getCellCount(); ++cell) {
				row.getCellByIndex(cell).setFont(font);
			}

			NamiMitglied m = participants.get(i);
			NamiSchulungenMap s = schulungen.get(i);

			//Name, Vorname
			row.getCellByIndex(0).setStringValue(m.getNachname() + ", " + m.getVorname());
			//Straße
			row.getCellByIndex(1).setStringValue(m.getStrasse());
			//PLZ Ort
			row.getCellByIndex(2).setStringValue(m.getPLZ() + " " + m.getOrt());
			//Alter
			if(!keinDatum){
				Date birthday = m.getGeburtsDatum();
				int diffInYears = calcAge(birthday, datum);
				row.getCellByIndex(3).setStringValue(String.valueOf(diffInYears));
			}
			//Funktion im Stamm

			//MLK(Jahr)
			if(schulungen.get(i).containsKey(NamiBaustein.MLK)) {
				row.getCellByIndex(6).setStringValue(getDateYearString(s.get(NamiBaustein.MLK).getDate()));
			}
			//WBK(Jahr/Stufe)
			if(schulungen.get(i).containsKey(NamiBaustein.WBK)) {
				row.getCellByIndex(6).setStringValue(getDateYearString(s.get(NamiBaustein.WBK).getDate()));
			}
			//1b
			if(schulungen.get(i).containsKey(NamiBaustein.BAUSTEIN_1B)) {
				row.getCellByIndex(7).setStringValue(getDateYearString(s.get(NamiBaustein.BAUSTEIN_1B).getDate()));
			}
			//2d(+)
			if(schulungen.get(i).containsKey(NamiBaustein.BAUSTEIN_2D)) {
				row.getCellByIndex(8).setStringValue(getDateYearString(s.get(NamiBaustein.BAUSTEIN_2D).getDate()));
			}
			//3b
			if(schulungen.get(i).containsKey(NamiBaustein.BAUSTEIN_3B)) {
				row.getCellByIndex(9).setStringValue(getDateYearString(s.get(NamiBaustein.BAUSTEIN_3B).getDate()));
			}
			//3c
			if(schulungen.get(i).containsKey(NamiBaustein.BAUSTEIN_3C)) {
				row.getCellByIndex(10).setStringValue(getDateYearString(s.get(NamiBaustein.BAUSTEIN_3C).getDate()));
			}
		}
	}

	@Override
	public int getMaxParticipantsPerPage() {
		return 0;
	}

	@Override
	protected String getResourceFileName() {
		return "Land_Leiter_Blanco.odt";
	}
}
