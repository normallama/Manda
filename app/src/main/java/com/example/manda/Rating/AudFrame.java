package com.example.manda.Rating;

public class AudFrame {
	double []fltFrame;//存放每一帧数据
	double fltSte;	//存放每一帧的短时能量
	int  dwZcr;	//存放每一帧的过零率
	boolean	 blVad;//判断这帧是否有效
	int AudFrmNext;//下一帧地址
}
