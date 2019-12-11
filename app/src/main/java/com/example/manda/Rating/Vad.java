package com.example.manda.Rating;
//����
public class Vad {
	public static double PI1 = 3.1415926536;// ����pi
	public static int FRM_LEN = 256;//%���ö�ʱ����Ҷ�任�ĳ��ȣ�ͬʱҲ�Ǻ������ĳ���
	public static int FRM_SFT = 80;// ����֡��
	public static double CHANGE = Math.pow(2, 15);
	public int FrmNum;// �ܹ�����֡
	public int dwSoundlen;// ���������
	public int predata[];// ����15�η�������һ��ǰ������
	public double data[];//����15�η�������һ���������
	public double fpData[];// Ԥ���غ�Ĳ�����
	public float fltHamm[];// Hamming��ϵ��
	public AudFrame audFrame[];// ֡����
	public double fltZcrVadThresh;//������ ��ֵ,0.02
	double fltSteThresh[];     //˫���޶�ʱ������ֵ[0]��[1]��   
	double	dwZcrThresh[];  //˫���޹�������ֵ[0]��[1]��   
	int   WavStart;//������ʼ��
	int   WavEnd;//����������
	public static int  MIN_WORD_LEN=15;//��С��������
	public static int  MAX_SLIENCE_LEN=8; 	//���������
	//public static int WORD_MAX_SLIENCE =10;  //���������������
	public double dwWordLen;//�˵���������γ���
	public double maxData;//���Ĳ�����
	public Vad(String filename) {
		 ReadWav(filename);
	}
	
	/***************************
	*MFCC�õĶ˵���
	*������: WaveEndtest(void)
	*���ܣ��˵���
	*************************/
	public void WaveEndtest() 
	{
		AudEnframe();//��֡��ok
		Hamming() ;//��hammingϵ��
		AudHamming();//�Ӵ�
		AudSte();//����ʱ����
		AudZcr();//��������
		AudNoiseEstimate();	//����������ֵ
		AudVadEstimate();//�˵���
	}
	
	

	/***********************************
	 * ��ȡ��Ƶ ��������ReadWav() ���ܣ���ȡ��Ƶ
	 ************************************/
	public void ReadWav(String filename) {
		WaveFileReader reader = new WaveFileReader(filename);
		if (reader.isSuccess()) {
			predata = reader.getData()[0]; // ��ȡ��һ����
			dwSoundlen = predata.length;//�����������ݳ���// ���������
		} else {
			System.err.println(filename + "����һ��������wav�ļ�");
		}
		data = new double[dwSoundlen];
		for(int i=0;i<dwSoundlen;i++){
			data[i]= predata[i]/CHANGE;
		}
		/*for(int i = 0; i < data.length; i++){   
			System.out.print(data[i]);  
        } */
		vadCommon();//��һ��
	}

	/***********************************
	 * Ԥ���� ��������AudPreEmphasize() ���ܣ������в��������Ԥ����
	 *  % Ԥ�����˲��� 
  	 * xx=double(x); 
     * xx=filter([1 -0.9375],1,xx); Ŀ����Ϊ�˶������ĸ�Ƶ���ֽ��м��أ�ȥ���ڴ������Ӱ�죬���������ĸ�Ƶ�ֱ��ʡ�
	 ************************************/

	public void AudPreEmphasize() {
		fpData = new double[dwSoundlen];
		fpData[0] = data[0];
		for (int i = 1; i < dwSoundlen; i++) {
			fpData[i] = (double) (data[i]) - (double) (data[i - 1]) * 0.9375;
			//y(n)=x(n)-ax(n-1)
			//https://blog.csdn.net/godloveyuxu/article/details/73555874
		}


	}

	/***********************************
	 * ��֡ ��������AudEnframe() ���ܣ���ÿһ֡��fltFrame[FRM_LEN]���������ֵ��������֡��
	 * fpData��Ԥ���غ������
	 * һ��ÿ���֡��ԼΪ33~100֡�������������
	 * һ��ķ�֡����Ϊ�����ֶεķ�����ǰһ֡�ͺ�һ֡�Ľ������ֳ�Ϊ֡�ƣ�֡����֡���ı�ֵһ��Ϊ0~0.5
	 * public static int FRM_LEN = 256;// ����֡��
	 * public static int FRM_SFT = 80;// ����֡��
	 * FrmNum�ܹ�֡����dwSoundlen���������
	 * http://www.ilovematlab.cn/forum.php?mod=viewthread&tid=162908
	 * ������һ��ʱ�̣���������Ӧ��֡�Ĵ������¡�
	 * % fs ������
	 * % t0 �ض�ʱ��
	 * frame_idx=fix((t0*fs-wlen)/nstep +1);
	 ************************************/
	public void AudEnframe() {
		FrmNum = (dwSoundlen - (FRM_LEN - FRM_SFT)) / FRM_SFT;//Nframe = floor( (length(x) - wlen) / nstep) + 1;��������֡���̣ܶ����һ֡������wlen���ȵģ��ɴ�Ͳ�Ҫ�ˡ����˴��š�
		audFrame = new AudFrame[FrmNum];//֡�Ľṹ
		for(int i=0;i<FrmNum;i++){
			audFrame[i] = new AudFrame();
		}
		int x = 0;// ÿһ֡����ʼ��
		for (int i = 0; i < FrmNum; i++) {
			audFrame[i].fltFrame = new double[FRM_LEN];//��Ÿ�֡��Ϣ
			
			for (int j = 0; j < FRM_LEN; j++) {
				audFrame[i].fltFrame[j] = data[x + j];//�ֿ�һ����������
			}
			x+=FRM_SFT;//��֡�Ƶ�����һ֡
		}
		
		
	}

	/***********************************
	 * ������ϵ�� ��������Hamming()
	 * ���ܣ�������ϵ�����������ÿһ֡��֡����Ҫ�õ�PI����������ǹ̶�ֵ��ֻ��֡������
	 * ���͵Ĵ��ڴ�С��25ms��֡����10ms������������Ϊ
	 * W(n,�� ) = (1 -�� ) - �� cos(2*PI*n/(N-1))��0�Qn�QN-1
	 * һ������£���ȡ0.46 ��
	 * public static int FRM_LEN = 256;//%���ö�ʱ����Ҷ�任�ĳ��ȣ�ͬʱҲ�Ǻ������ĳ���
	 * http://blog.chinaunix.net/uid-26715658-id-3187231.html
	 ************************************/
	public void Hamming() {
		fltHamm = new float[FRM_LEN];//
		for (int i = 0; i < FRM_LEN; i++) {
			// ����������ΪW(n,a) = (1-a) -��cos(2*PI*n/(N-1))
			// 0�Qn�QN-1,aһ��ȡ0.46
			// �˴�ȡ0.46
			// ʹ��Ƶ����ƽ��sin����
			fltHamm[i] = (float)(0.54 - 0.46*Math.cos((2*i*PI1) / (FRM_LEN-1)));
		}
	}

	/***********************************
	 * �Ӵ� ��������AudHamming()
	 * ���ܣ��������ÿһ֡��֡������Ҫ���õ���õĺ�����ϵ����������ÿ���������ֵ���Ժ�����ϵ�����ٰѽ������fltFrame[]
	 * for m=1:N                       
	 * %�ú�������ȡ�źţ�����ΪN����Ҫ��Ϊ�˼��ٽض������դ��ЧӦ��
	 * b(m)=a(m)*h(m)
	 ************************************/
	public void AudHamming() {
		for (int i = 0; i < FrmNum; i++) {
			// �Ӵ�
			for (int j = 0; j < FRM_LEN; j++) {//ԭ����i < FRM_LEN; i++
				// ���������ź��и�֡��Ӧ�ĺ�����ϵ��
				audFrame[i].fltFrame[j] *= fltHamm[j];
			}
		}
	}

	//not ok
	/***********************************
	 * ÿһ֡��ʱ���� ��������AudSte()
	 * ���ܣ���ÿһ֡�Ķ�ʱ����������������һ֡����������ֵ��ӣ�fpFrmSnd��֡��һ����
	 * FrmNum�ܹ�֡��,FRM_LEN֡��������
	 * https://blog.csdn.net/rocketeerLi/article/details/83271399��ʱƽ��������Ӧ���������źŵ�ƽ����
	 * https://blog.csdn.net/godloveyuxu/article/details/77456692��ʱƽ������
	 * ������
	 * https://bbs.csdn.net/topics/390936836֡���Ͳ�����
	 * https://blog.csdn.net/Barry_J/article/details/84177006��ʱƽ������python
	 ************************************/

	public void AudSte() {	
		for (int i = 0; i < FrmNum; i++) {
			double fltShortEnergy = 0;
			for (int j = 0; j < FRM_LEN; j++) {
				fltShortEnergy += (audFrame[i].fltFrame[j])*(audFrame[i].fltFrame[j]);//Math.abs(audFrame[i].fltFrame[j])Ӧ���������ƽ����
			}
			audFrame[i].fltSte = fltShortEnergy;
		}
		
		
		
	}
	
	/***********************************
	*һ֡�Ĺ�����
	*��������AudZcr(fltSound *fpFrmSnd, DWORD FrmLen,fltSound ZcrThresh)
	*���ܣ����һ֡�Ĺ����ʣ�fpFrmSnd֡��һ���������ַ��FrmLen֡����ZcrThresh�����ʷ�ֵ
	************************************/
	public void AudZcr(){
		
		fltZcrVadThresh = 0.02;
		for( int i = 0; i < FrmNum; i++)
		{
			int    dwZcrRate = 0;
		for(int j =0 ; j < FRM_LEN - 1; j++)//����ʦ�ֺ����д�����ֵ��j-1
			//if((audFrame[i].fltFrame[j]*audFrame[i].fltFrame[j + 1] < 0)&&((audFrame[i].fltFrame[j] - audFrame[i].fltFrame[j+1]) > fltZcrVadThresh))
		{
			if((audFrame[i].fltFrame[j]*audFrame[i].fltFrame[j + 1] < 0)&&((audFrame[i].fltFrame[j+1] - audFrame[i].fltFrame[j]) > fltZcrVadThresh))
				dwZcrRate++;
		}
		audFrame[i].dwZcr=dwZcrRate;
		}

		
	}
	
	
	/**********************************
	*����������ֵ
	*�������� AudNoiseEstimate����
	*���ܣ�����˫���޷�ֵ
	*zcr������
	*ste����
    ***********************************/
	
	public void AudNoiseEstimate(){
		fltSteThresh = new double[2];
		dwZcrThresh = new double [2];
		dwZcrThresh[0] = 10;
		dwZcrThresh[1] = 5;
		fltSteThresh[0] = 10;
		fltSteThresh[1] = 2;
		double maxSte = 0;
		for(int i = 0; i < FrmNum; i++)  {
			if(maxSte<audFrame[i].fltSte)
				maxSte = audFrame[i].fltSte;//�õ����Ķ�ʱ����
		}
		
		fltSteThresh[0] = fltSteThresh[0]<(maxSte/4)?fltSteThresh[0]:(maxSte/4);//��ýϸߵ�������ֵ
		fltSteThresh[1] = fltSteThresh[1]<(maxSte/8)?fltSteThresh[1]:(maxSte/8);//��ýϵ͵�������ֵ
		
	}
	
	
	public void AudVadEstimate(){
		//Extract Threshold
		double	ZcrLow=dwZcrThresh[1];//˫���޹�������ֵ[0]��[1]��   
		double	ZcrHigh=dwZcrThresh[0];
		double	AmpLow=fltSteThresh[1];//˫���޶�ʱ������ֵ[0]��[1]��
		double	AmpHigh=fltSteThresh[0];
		WavStart=0;
		WavEnd=0;
		int status =0;
		int count =0;
		int silence =0;
		
		for(int i=0;i<FrmNum;i++)
		{
			switch(status)
			{
			case 0:
			case 1:
				if ((audFrame[i].fltSte)>AmpHigh)//�����ȼ�����������
				{
					WavStart = (i-count-1)>1?(i-count-1):1;//���
					status= 2;
					silence = 0;
					count = count + 1;
				}
				//������//����Ͷ�ʱ�������ߵ���������ֵ
				else if((audFrame[i].fltSte)>AmpLow || (audFrame[i].dwZcr)>ZcrLow)
				{
					status =1;
					count = count +1;
				}
				else
				{
				status=0;
				count =0;
				}
				break;

			case 2: //Speech Section
				//������//����Ͷ�ʱ�������ߵ���������ֵ
				if((audFrame[i].fltSte > AmpLow) || (audFrame[i].dwZcr > ZcrLow))
				{
					count = count +1;
					//WavEnd=i-Silence;
				}
				else
				{
					silence = silence+1;
					if (silence < MAX_SLIENCE_LEN) //����С�����������
					{	
						count = count +1;
					}
					else if(count< MIN_WORD_LEN)   //��С��������
					{	
						status  = 0;
						silence = 0;
						count = 0;
					}
					else
					{
						status = 3;
					}
				}
				break;
			default:
				break;
			}
			//��������֡
		}
		count = count-silence/2;
		WavEnd = WavStart + count -1;

	}
	/***************************
	*��һ��
	*������: vadCommon(void)
	*���ܣ����������й�һ��
	*************************/
	public void vadCommon(){
		for( int i = 0; i < dwSoundlen; i++)//��������
		{
		if(maxData<Math.abs(data[i]))//�ҳ�����ֵ��������
			maxData=Math.abs(data[i]);
		}
		for( int i = 0; i < dwSoundlen; i++)//����ͳһ
		{
			data[i] = data[i]/maxData;
		}
	}
}
