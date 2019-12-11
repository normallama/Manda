package com.example.manda.Rating;
//采样
public class Vad {
	public static double PI1 = 3.1415926536;// 定义pi
	public static int FRM_LEN = 256;//%设置短时傅里叶变换的长度，同时也是汉明窗的长度
	public static int FRM_SFT = 80;// 定义帧移
	public static double CHANGE = Math.pow(2, 15);
	public int FrmNum;// 总共多少帧
	public int dwSoundlen;// 采样点个数
	public int predata[];// 除以15次方，化归一化前采样点
	public double data[];//除以15次方，化归一化后采样点
	public double fpData[];// 预加重后的采样点
	public float fltHamm[];// Hamming窗系数
	public AudFrame audFrame[];// 帧数组
	public double fltZcrVadThresh;//过零率 阀值,0.02
	double fltSteThresh[];     //双门限短时能量阈值[0]高[1]低   
	double	dwZcrThresh[];  //双门限过零率阈值[0]高[1]低   
	int   WavStart;//语音起始点
	int   WavEnd;//语音结束点
	public static int  MIN_WORD_LEN=15;//最小语音长度
	public static int  MAX_SLIENCE_LEN=8; 	//最大静音长度
	//public static int WORD_MAX_SLIENCE =10;  //语音间最大静音距离
	public double dwWordLen;//端点检测后语音段长度
	public double maxData;//最大的采样点
	public Vad(String filename) {
		 ReadWav(filename);
	}
	
	/***************************
	*MFCC用的端点检测
	*函数名: WaveEndtest(void)
	*功能：端点检测
	*************************/
	public void WaveEndtest() 
	{
		AudEnframe();//分帧，ok
		Hamming() ;//求hamming系数
		AudHamming();//加窗
		AudSte();//求解短时能量
		AudZcr();//求解过零率
		AudNoiseEstimate();	//估计噪声阈值
		AudVadEstimate();//端点检测
	}
	
	

	/***********************************
	 * 读取音频 函数名：ReadWav() 功能：读取音频
	 ************************************/
	public void ReadWav(String filename) {
		WaveFileReader reader = new WaveFileReader(filename);
		if (reader.isSuccess()) {
			predata = reader.getData()[0]; // 获取第一声道
			dwSoundlen = predata.length;//声道所含数据长度// 采样点个数
		} else {
			System.err.println(filename + "不是一个正常的wav文件");
		}
		data = new double[dwSoundlen];
		for(int i=0;i<dwSoundlen;i++){
			data[i]= predata[i]/CHANGE;
		}
		/*for(int i = 0; i < data.length; i++){   
			System.out.print(data[i]);  
        } */
		vadCommon();//归一化
	}

	/***********************************
	 * 预加重 函数名：AudPreEmphasize() 功能：对所有采样点进行预处理
	 *  % 预加重滤波器 
  	 * xx=double(x); 
     * xx=filter([1 -0.9375],1,xx); 目的是为了对语音的高频部分进行加重，去除口唇辐射的影响，增加语音的高频分辨率。
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
	 * 分帧 函数名：AudEnframe() 功能：给每一帧的fltFrame[FRM_LEN]赋采样点的值，个数是帧长
	 * fpData是预加重后的数据
	 * 一般每秒的帧数约为33~100帧，视情况而定。
	 * 一般的分帧方法为交叠分段的方法，前一帧和后一帧的交叠部分称为帧移，帧移与帧长的比值一般为0~0.5
	 * public static int FRM_LEN = 256;// 定义帧长
	 * public static int FRM_SFT = 80;// 定义帧移
	 * FrmNum总共帧数，dwSoundlen采样点个数
	 * http://www.ilovematlab.cn/forum.php?mod=viewthread&tid=162908
	 * 而给定一个时刻，想求得其对应的帧的代码如下。
	 * % fs 采样率
	 * % t0 特定时刻
	 * frame_idx=fix((t0*fs-wlen)/nstep +1);
	 ************************************/
	public void AudEnframe() {
		FrmNum = (dwSoundlen - (FRM_LEN - FRM_SFT)) / FRM_SFT;//Nframe = floor( (length(x) - wlen) / nstep) + 1;？？？若帧都很短，最后一帧，不够wlen长度的，干脆就不要了。无伤大雅。
		audFrame = new AudFrame[FrmNum];//帧的结构
		for(int i=0;i<FrmNum;i++){
			audFrame[i] = new AudFrame();
		}
		int x = 0;// 每一帧的起始点
		for (int i = 0; i < FrmNum; i++) {
			audFrame[i].fltFrame = new double[FRM_LEN];//存放各帧信息
			
			for (int j = 0; j < FRM_LEN; j++) {
				audFrame[i].fltFrame[j] = data[x + j];//分开一个个采样点
			}
			x+=FRM_SFT;//加帧移到达下一帧
		}
		
		
	}

	/***********************************
	 * 汉明窗系数 函数名：Hamming()
	 * 功能：求汉明窗系数，输入的是每一帧的帧长，要用到PI。这个数组是固定值，只有帧长决定
	 * 典型的窗口大小是25ms，帧移是10ms。汉明窗函数为
	 * W(n,α ) = (1 -α ) - α cos(2*PI*n/(N-1))，0≦n≦N-1
	 * 一般情况下，α取0.46 。
	 * public static int FRM_LEN = 256;//%设置短时傅里叶变换的长度，同时也是汉明窗的长度
	 * http://blog.chinaunix.net/uid-26715658-id-3187231.html
	 ************************************/
	public void Hamming() {
		fltHamm = new float[FRM_LEN];//
		for (int i = 0; i < FRM_LEN; i++) {
			// 汉明窗函数为W(n,a) = (1-a) -αcos(2*PI*n/(N-1))
			// 0≦n≦N-1,a一般取0.46
			// 此处取0.46
			// 使音频波段平滑sin（）
			fltHamm[i] = (float)(0.54 - 0.46*Math.cos((2*i*PI1) / (FRM_LEN-1)));
		}
	}

	/***********************************
	 * 加窗 函数名：AudHamming()
	 * 功能：输入的是每一帧的帧长，需要利用到求得的汉明窗系数，具体是每个采样点的值乘以汉明窗系数，再把结果赋予fltFrame[]
	 * for m=1:N                       
	 * %用汉明窗截取信号，长度为N，主要是为了减少截断引起的栅栏效应等
	 * b(m)=a(m)*h(m)
	 ************************************/
	public void AudHamming() {
		for (int i = 0; i < FrmNum; i++) {
			// 加窗
			for (int j = 0; j < FRM_LEN; j++) {//原来：i < FRM_LEN; i++
				// 保存语音信号中各帧对应的汉明窗系数
				audFrame[i].fltFrame[j] *= fltHamm[j];
			}
		}
	}

	//not ok
	/***********************************
	 * 每一帧短时能量 函数名：AudSte()
	 * 功能：求每一帧的短时能量，即将所有这一帧的所有样点值相加，fpFrmSnd是帧第一个样
	 * FrmNum总共帧数,FRM_LEN帧长，窗长
	 * https://blog.csdn.net/rocketeerLi/article/details/83271399短时平均能量，应该是所有信号的平方和
	 * https://blog.csdn.net/godloveyuxu/article/details/77456692短时平均能量
	 * ？？？
	 * https://bbs.csdn.net/topics/390936836帧长和采样点
	 * https://blog.csdn.net/Barry_J/article/details/84177006短时平均能量python
	 ************************************/

	public void AudSte() {	
		for (int i = 0; i < FrmNum; i++) {
			double fltShortEnergy = 0;
			for (int j = 0; j < FRM_LEN; j++) {
				fltShortEnergy += (audFrame[i].fltFrame[j])*(audFrame[i].fltFrame[j]);//Math.abs(audFrame[i].fltFrame[j])应该是这个的平方和
			}
			audFrame[i].fltSte = fltShortEnergy;
		}
		
		
		
	}
	
	/***********************************
	*一帧的过零率
	*函数名：AudZcr(fltSound *fpFrmSnd, DWORD FrmLen,fltSound ZcrThresh)
	*功能：求解一帧的过零率，fpFrmSnd帧第一个采样点地址，FrmLen帧长，ZcrThresh过零率阀值
	************************************/
	public void AudZcr(){
		
		fltZcrVadThresh = 0.02;
		for( int i = 0; i < FrmNum; i++)
		{
			int    dwZcrRate = 0;
		for(int j =0 ; j < FRM_LEN - 1; j++)//智明师兄后面有带绝对值，j-1
			//if((audFrame[i].fltFrame[j]*audFrame[i].fltFrame[j + 1] < 0)&&((audFrame[i].fltFrame[j] - audFrame[i].fltFrame[j+1]) > fltZcrVadThresh))
		{
			if((audFrame[i].fltFrame[j]*audFrame[i].fltFrame[j + 1] < 0)&&((audFrame[i].fltFrame[j+1] - audFrame[i].fltFrame[j]) > fltZcrVadThresh))
				dwZcrRate++;
		}
		audFrame[i].dwZcr=dwZcrRate;
		}

		
	}
	
	
	/**********************************
	*估计噪声阀值
	*函数名： AudNoiseEstimate（）
	*功能：计算双门限阀值
	*zcr过零率
	*ste能量
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
				maxSte = audFrame[i].fltSte;//得到最大的短时能量
		}
		
		fltSteThresh[0] = fltSteThresh[0]<(maxSte/4)?fltSteThresh[0]:(maxSte/4);//获得较高的能量阈值
		fltSteThresh[1] = fltSteThresh[1]<(maxSte/8)?fltSteThresh[1]:(maxSte/8);//获得较低的能量阈值
		
	}
	
	
	public void AudVadEstimate(){
		//Extract Threshold
		double	ZcrLow=dwZcrThresh[1];//双门限过零率阈值[0]高[1]低   
		double	ZcrHigh=dwZcrThresh[0];
		double	AmpLow=fltSteThresh[1];//双门限短时能量阈值[0]高[1]低
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
				if ((audFrame[i].fltSte)>AmpHigh)//首先先检测出浊音部分
				{
					WavStart = (i-count-1)>1?(i-count-1):1;//获得
					status= 2;
					silence = 0;
					count = count + 1;
				}
				//？？？//过最低短时能量或者到过零率阈值
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
				//？？？//过最低短时能量或者到过零率阈值
				if((audFrame[i].fltSte > AmpLow) || (audFrame[i].dwZcr > ZcrLow))
				{
					count = count +1;
					//WavEnd=i-Silence;
				}
				else
				{
					silence = silence+1;
					if (silence < MAX_SLIENCE_LEN) //静音小于最大静音长度
					{	
						count = count +1;
					}
					else if(count< MIN_WORD_LEN)   //最小语音长度
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
			//更新语音帧
		}
		count = count-silence/2;
		WavEnd = WavStart + count -1;

	}
	/***************************
	*归一化
	*函数名: vadCommon(void)
	*功能：对语音进行归一化
	*************************/
	public void vadCommon(){
		for( int i = 0; i < dwSoundlen; i++)//样本点数
		{
		if(maxData<Math.abs(data[i]))//找出绝对值最大的数据
			maxData=Math.abs(data[i]);
		}
		for( int i = 0; i < dwSoundlen; i++)//数据统一
		{
			data[i] = data[i]/maxData;
		}
	}
}
