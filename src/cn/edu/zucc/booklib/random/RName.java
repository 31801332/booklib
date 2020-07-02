package cn.edu.zucc.booklib.random;

import java.util.Random;

public class RName {
	private static String xings = "�� Ǯ �� �� �� �� ֣ �� �� �� �� �� " + "�� �� �� �� �� �� �� �� �� �� ʩ �� " + "�� �� �� �� �� κ �� �� �� л �� �� "
			+ "�� ˮ � �� �� �� �� �� �� �� �� �� " + "��ٹ ˾�� �Ϲ� ŷ�� �ĺ� ��� ���� ���� ���� �ʸ� ξ�� ����";
	private static String mings = "�̷������ա����㡢���̡����Ρ����̡��������黱��ƽ������䡢��硢���ɡ����ơ�" + "���ɡ��������ΰء����ס��������������桢�ûܡ����ࡢЦ�������䡢���ء���ѩ��"
			+ "�ַ㡢��ޱ�����㡢Ѱ������ɽ���Ӻ������㡢�ٲ����������������²������桢���١�" + "������ܡ���ɽ��ǧ�١����졢��ܽ����ɽ�����������������������ϡ����ס����ޡ�"
			+ "��˪�����ۡ����㡢���ơ�Ѱ�ġ���ѩ�����桢���ա����������١��໱�����������Ρ�" + "ϧѩ���𺣡�֮�ᡢ����";
	private static Random r = new Random();

	/**
	 * ʹ��ָ�������ϣ���xings������֣������������
	 *
	 * @param xing   ָ������
	 * @param length ָ�������ܳ���
	 * @return
	 */
	public static String build(String xing, int length) {
		// ��������
		String xingming = xing;

		//�����������
		Random r = new Random();

		while (xingming.length() < length) {
			// �����ַ��������ȡ��һ���ַ��ı��
			int index = r.nextInt(mings.length());
			// �������ַ�����ȡһ����
			String s = mings.substring(index, index + 1);
			// ���s�Ƕٺţ���������ȡһ�Σ�ʹ�ó����ȱ����������Ƽ��ķ�ʽ��
			if ("��".equals(s)) {
				continue;
			} else {
				//���� �ӵ� ������ȡ
				xingming += s;
			}
		}
		return xingming;
	}

	/**
	 * ʹ��xings�������ϣ���xings������֣������������
	 *
	 * @param length ָ�������ܳ���
	 * @return
	 */
	public static String build(int length) {
		// �ж������ĳ��ȱ������ 2
		if (length < 2) {
			System.out.println("������������2���ַ�");
			return null;
		}
		/**
		 * ��Ϊ��Щ���ϲ��ܲ�֣��磺���գ������ѡ���ϲ�����ѡ��������һ��һ����ѡ
		 * ���Ҫ��xingsת������
		 */
		// �����ѡ����
		Random r = new Random();
		String[] xingArr = xings.split(" ");
		int index = r.nextInt(xingArr.length);
		String xing = xingArr[index];

		// �������ϣ��ٵ��������ѡ���ֵķ�������OK��
		return build(xing, length);
	}

	/**
	 * �������2~3���ֵ�����
	 *
	 * @return
	 */
	public String build() {
		int length = r.nextInt(2) + 2;
		return build(length);
	}

	public static void main(String[] args) {
		System.out.println(new RName().build());
	}
}
