package applet;

import javacard.framework.*;
import javacard.security.RandomData;

//TODO: maybe upload class for each part of the transaction?
/**
 * meaning class for: uploading keys, uploading
 */

public class MainApplet extends Applet implements MultiSelectable
{
	/**
	 * class of all instructions and other hardcoded information
	 */
	private static final AppletInstructions instructions = new AppletInstructions();

	private static int myOffset;
	short inputDataLength;

	private byte[] psbt = JCSystem.makeTransientByteArray(instructions.getBUFFER_SIZE(),
			JCSystem.CLEAR_ON_DESELECT);
	private byte[] tmpBuffer = JCSystem.makeTransientByteArray(instructions.getBUFFER_SIZE(),
			JCSystem.CLEAR_ON_DESELECT);

	private RandomData random; // gonna delete this later on


	public static void install(byte[] bArray, short bOffset, byte bLength)
	{
		new MainApplet(bArray, bOffset, bLength);
	}
	
	public MainApplet(byte[] buffer, short offset, byte length)
	{
		random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
		register();
	}

	public void process(APDU apdu)
	{
		byte[] apduBuffer = apdu.getBuffer();
		byte cla = apduBuffer[ISO7816.OFFSET_CLA];
		byte ins = apduBuffer[ISO7816.OFFSET_INS];
		short lc = apduBuffer[ISO7816.OFFSET_LC];
		short p1 = apduBuffer[ISO7816.OFFSET_P1]; // this means offset for me
		short p2 = apduBuffer[ISO7816.OFFSET_P2]; // this is (sizeOfPsbt//256 + 1)
		short dataOffset = apduBuffer[ISO7816.OFFSET_CDATA];

		//inputDataLength = apdu.setIncomingAndReceive();

		//System.out.print("CDATA offset is: " + dataOffset + System.lineSeparator());
		//System.out.print("Buffer length is:" + apduBuffer.length + System.lineSeparator());
		//Util.arrayCopyNonAtomic(apduBuffer, (short) 5, psbt, (short) 0, (short) 63);
		//psbt[BUFFER_SIZE] = (byte) 0x0;
		//System.out.print("DATA to String is: " + new String(psbt, Charset.defaultCharset()) + System.lineSeparator());
		//System.out.print(lc + System.lineSeparator());

		//Util.arrayFillNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, (short) 10, (byte) 1);
		//apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 10);

		/**
		 * test instructions down here
		 */
		if (cla == instructions.getCLASS_PRINT() && ins == instructions.getPRINT()) {
			System.out.print(psbt +System.lineSeparator());
		}
		if (cla == instructions.getCLASS_UPLOAD() && ins == instructions.getUPLOAD()
				&& myOffset == 0) {
			System.out.print("Transition: " + System.lineSeparator());
		}

		/**
		 * only problem test if
		 */
		if (cla == instructions.getCLASS_UPLOAD() && ins == instructions.getUPLOAD()
				&& (p2 * instructions.getPACKET_BUFFER_SIZE() > psbt.length)) {
			System.out.print("trying to transfer packet bigger than maximum!" + System.lineSeparator());
		}
		if (cla == instructions.getCLASS_UPLOAD() && ins == instructions.getUPLOAD()) {
			myOffset = myOffset * instructions.getPACKET_BUFFER_SIZE();
			System.out.print("myOffset: " + myOffset + System.lineSeparator());
			Util.arrayCopyNonAtomic(apduBuffer, (short) 5, psbt, (short) myOffset, instructions.getPACKET_BUFFER_SIZE());
		}
		if (cla == instructions.getCLASS_UPLOAD() && ins == instructions.getUPLOAD_FINISH()) {
			System.out.print("UPLOAD_FINISH instruction acknowledged" + System.lineSeparator());
			apdu.setOutgoingAndSend((short) 0, (short) 0);
			// TODO: find what code has UNIX for success
		}
		if (cla == instructions.getCLASS_UPLOAD() && ins == instructions.getUPLOAD_REQUEST()) {
			apdu.setOutgoingAndSend(instructions.getPACKET_BUFFER_SIZE(), (short) 0);
		}
	}

	public boolean select(boolean b) {
		return true;
	}

	public void deselect(boolean b) {

	}
}
