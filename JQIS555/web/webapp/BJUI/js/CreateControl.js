//1������ GridReportCodeBase ָ���˱�����������λ����汾�ţ����ͻ��˳��η��ʱ���
//   ʱ�����汾��������ʣ����Զ����ز���װ Grid++Report ��������Ӧ������ 
//   GridReportCodeBase ��ֵ����Ϊ��ʵ�������
//2��codebase �Ⱥź���Ĳ����� griectl.cab �����ص�ַ����������Ե�ַ��һ�����վ��
//   ��Ŀ¼��ʼѰַ��griectl.cab һ��Ҫ������ָ��Ŀ¼�¡�
//3��Version �Ⱥź���Ĳ����ǲ����װ���İ汾�ţ�������°汾�����װ����Ӧ�ϴ��°�
//   �� griectl.cab ����������ӦĿ¼������������İ汾�š�
//4��������ϸ��Ϣ��ο������С�������(WEB����)->�ڷ�������������װ��������
//var GridReportCodeBase = 'codebase="/griectl.cab#Version=5,1,10,418"';
var GridReportCodeBase = 'codebase="${ctx}/griectl.cab#Version=5,5,11,518"';
//�������ע�����������ע���û�����ע����滻���������ֵ
var UserName = '�Ϸʾ�����ӿƼ����޹�˾';
var SerialNo = 'LKNB9ARPQ4NLDP58H1B9JKA938IYG4HGXI8UJ6R911ZF4240E8TJUG9NDLF6WLH08';

//����������󣬱�������ǲ��ɼ��Ķ�����ϸ��鿴�����е� IGridppReport
function CreateReport(Name)
{
	document.write('<OBJECT id="' + Name + '" classid="CLSID:50CA95AF-BDAA-4C69-A9C6-93E1136E68BC" ' + GridReportCodeBase + '" VIEWASTEXT></OBJECT>');
}

//���������ӡ��ʾ�������ϸ��鿴�����е� IGRPrintViewer
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
function CreatePrintViewer(ReportURL, DataURL)
{
	document.write('<OBJECT classid="CLSID:9E4CCA44-17FC-402b-822C-BFA6CBA77C0C" '+ GridReportCodeBase + ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>');
	document.write('<param name="ReportURL" value="' + ReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write('</OBJECT>');
}
//���������ӡ��ʾ�������ϸ��鿴�����е� IGRPrintViewer
//sender - ��Ҫд��ؼ�ID
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
function XHYCreatePrintViewer(sender, ReportURL, DataURL) {
    var var1 = document.getElementById(sender);
    var var2 = '<OBJECT classid="CLSID:9E4CCA44-17FC-402b-822C-BFA6CBA77C0C" ' + GridReportCodeBase + ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>';
    var2 += '<param name="ReportURL" value="' + ReportURL + '">';
    var2 += '<param name="DataURL" value="' + DataURL + '">';
    var2 += '<param name="SerialNo" value="' + SerialNo + '">';
    var2 += '<param name="UserName" value="' + UserName + '"></OBJECT>';
    var1.innerHTML = var2;
}
//���������ӡ��ʾ�������ϸ��鿴�����е� IGRPrintViewer
//sender - ��Ҫд��ؼ�ID
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
function CreatePrintViewerXML(sender, ReportURL) {
    var var1 = document.getElementById(sender);
    var var2 = '<OBJECT classid="CLSID:9E4CCA44-17FC-402b-822C-BFA6CBA77C0C" ' + GridReportCodeBase + ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>';
    var2 += '<param name="ReportURL" value="' + ReportURL + '">';
    var2 += '<param name="SerialNo" value="' + SerialNo + '">';
    var2 += '<param name="wmode" value="transparent">';
    var2 += '<param name="UserName" value="' + UserName + '">';
    var2 += '<param name="ShowToolbar" value="false">'
    var2 += '</OBJECT>';
    var1.innerHTML = var2;
}

//���������ѯ��ʾ�������ϸ��鿴�����е� IGRDisplayViewer
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
function CreateDisplayViewer(ReportURL, DataURL)
{
	document.write('<OBJECT classid="CLSID:E060AFE6-5EFF-4830-B7F0-093ECC08EF37" '+ GridReportCodeBase + ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>');
	document.write('<param name="ReportURL" value="' + ReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write('</OBJECT>');
}

function XHYCreateDisplayViewer(sender, ReportURL, DataURL) {
    var var1 = document.getElementById(sender);
    var var2 = '<OBJECT classid="CLSID:E060AFE6-5EFF-4830-B7F0-093ECC08EF37" ' + GridReportCodeBase + ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>';
    var2 += '<param name="ReportURL" value="' + ReportURL + '">';
    if(DataURL!=undefined&& DataURL!=""){
    	var2 += '<param name="DataURL" value="' + DataURL + '">';
    }
    var2 += '<param name="SerialNo" value="' + SerialNo + '">';
    var2 += '<param name="UserName" value="' + UserName + '"></OBJECT>';
    var1.innerHTML = var2;
}

///��������������������ϸ��鿴�����е� IGRDesigner
//LoadReportURL - ��ȡ����ģ���URL������ʱ�Ӵ�URL���뱨��ģ�����ݲ����ص���������
//SaveReportURL - ���汨��ģ���URL��������ƺ�Ľ�����ݣ��ɴ�URL�ķ�����WEB����˽�����ģ��־ñ���
//DataURL - ��ȡ��������ʱ���ݵ�URL����������н����ӡ��ͼ���ѯ��ͼʱ�Ӵ�URL��ȡ��������
function CreateDesigner(LoadReportURL, SaveReportURL, DataURL)
{
	document.write('<OBJECT classid="CLSID:76AB1C26-34A0-4898-A90B-74CCFF435C43" '+ GridReportCodeBase + ' width="100%" height="100%" id="ReportDesigner" VIEWASTEXT>');
	document.write('<param name="LoadReportURL" value="' + LoadReportURL + '">');
	document.write('<param name="SaveReportURL" value="' + SaveReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write('</OBJECT>');
}

//�ø���Ĳ������������ӡ��ʾ�������ϸ��鿴�����е� IGRPrintViewer
//Width - �������ʾ��ȣ�"100%"Ϊ������ʾ�����ȣ�"500"��ʾ500����Ļ���ص�
//Height - �������ʾ�߶ȣ�"100%"Ϊ������ʾ����߶ȣ�"500"��ʾ500����Ļ���ص�
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
//AutoRun - ָ������ڴ���֮���Ƿ��Զ����ɲ�չ�ֱ���,ֵΪfalse��true
//ExParams - ָ������Ĳ�����Բ���,����: "<param name="%ParamName%" value="%Value%">"�����Ĳ�����
function CreatePrintViewerEx(Width, Height, ReportURL, DataURL, AutoRun, ExParams)
{
	document.write('<OBJECT classid="CLSID:9E4CCA44-17FC-402b-822C-BFA6CBA77C0C" '+ GridReportCodeBase);
	document.write(' width="' + Width + '" height="' + Height + '" id="ReportViewer" VIEWASTEXT>');
	document.write('<param name="ReportURL" value="' + ReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="AutoRun" value="' + AutoRun + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write(ExParams);
	document.write('</OBJECT>');
}
//�ø���Ĳ������������ӡ��ʾ�������ϸ��鿴�����е� IGRPrintViewer
//sender - ��Ҫд��ؼ�ID
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
//AutoRun - ָ������ڴ���֮���Ƿ��Զ����ɲ�չ�ֱ���,ֵΪfalse��true
//ExParams - ָ������Ĳ�����Բ���,����: "<param name="%ParamName%" value="%Value%">"�����Ĳ�����
function XHYCreatePrintViewerEx(sender, ReportURL, DataURL, AutoRun, ExParams) {
    var var1 = document.getElementById(sender);
    var var2 = '<OBJECT classid="CLSID:9E4CCA44-17FC-402b-822C-BFA6CBA77C0C" ' + GridReportCodeBase;
    var2 += ' width="100%" height="100%" id="ReportViewer" VIEWASTEXT>';
    var2 += '<param name="ReportURL" value="' + ReportURL + '">';
    var2 += '<param name="DataURL" value="' + DataURL + '">';
    var2 += '<param name="AutoRun" value="' + AutoRun + '">';
    var2 += '<param name="SerialNo" value="' + SerialNo + '">';
    var2 += '<param name="UserName" value="' + UserName + '">';
    var2 += ExParams;
    var2 += '</OBJECT>';
    var1.innerHTML = var2;

}

//�ø���Ĳ������������ѯ��ʾ�������ϸ��鿴�����е� IGRDisplayViewer
//Width - �������ʾ��ȣ�"100%"Ϊ������ʾ�����ȣ�"500"��ʾ500����Ļ���ص�
//Height - �������ʾ�߶ȣ�"100%"Ϊ������ʾ����߶ȣ�"500"��ʾ500����Ļ���ص�
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
//AutoRun - ָ������ڴ���֮���Ƿ��Զ����ɲ�չ�ֱ���,ֵΪfalse��true
//ExParams - ָ������Ĳ�����Բ���,����: "<param name="%ParamName%" value="%Value%">"�����Ĳ�����
function CreateDisplayViewerEx(Width, Height, ReportURL, DataURL, AutoRun, ExParams)
{
	document.write('<OBJECT classid="CLSID:E060AFE6-5EFF-4830-B7F0-093ECC08EF37" '+ GridReportCodeBase);
	document.write(' width="' + Width + '" height="' + Height + '" id="ReportViewer" VIEWASTEXT>');
	document.write('<param name="ReportURL" value="' + ReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="AutoRun" value="' + AutoRun + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write(ExParams);
	document.write('</OBJECT>');
}
//�ø���Ĳ������������ѯ��ʾ�������ϸ��鿴�����е� IGRDisplayViewer
//sender - ��Ҫд��ؼ�ID
//ReportURL - ��ȡ����ģ���URL
//DataURL - ��ȡ�������ݵ�URL
//AutoRun - ָ������ڴ���֮���Ƿ��Զ����ɲ�չ�ֱ���,ֵΪfalse��true
//ExParams - ָ������Ĳ�����Բ���,����: "<param name="%ParamName%" value="%Value%">"�����Ĳ�����
function XHYCreateDisplayViewer(sender, ReportURL, DataURL, AutoRun, ExParams, height) {
    var var1 = document.getElementById(sender);
    var var2 = '<OBJECT classid="CLSID:E060AFE6-5EFF-4830-B7F0-093ECC08EF37" '+ GridReportCodeBase;
    var2 += ' width="100%" height="' + height + '" id="ReportViewer" VIEWASTEXT>';
    var2 += '<param name="ReportURL" value="' + ReportURL + '">';
    var2 += '<param name="DataURL" value="' + DataURL + '">';
    var2 += '<param name="AutoRun" value="' + AutoRun + '">';
    var2 += '<param name="SerialNo" value="' + SerialNo + '">';
    var2 += '<param name="UserName" value="' + UserName + '">';
    var2 += ExParams;
    var2 += '</OBJECT>';
    var1.innerHTML = var2;
}

///�ø���Ĳ�����������������������ϸ��鿴�����е� IGRDesigner
//Width - �������ʾ��ȣ�"100%"Ϊ������ʾ�����ȣ�"500"��ʾ500����Ļ���ص�
//Height - �������ʾ�߶ȣ�"100%"Ϊ������ʾ����߶ȣ�"500"��ʾ500����Ļ���ص�
//LoadReportURL - ��ȡ����ģ���URL������ʱ�Ӵ�URL���뱨��ģ�����ݲ����ص���������
//SaveReportURL - ���汨��ģ���URL��������ƺ�Ľ�����ݣ��ɴ�URL�ķ�����WEB����˽�����ģ��־ñ���
//DataURL - ��ȡ��������ʱ���ݵ�URL����������н����ӡ��ͼ���ѯ��ͼʱ�Ӵ�URL��ȡ��������
function CreateDesignerEx(Width, Height, LoadReportURL, SaveReportURL, DataURL, ExParams)
{
	document.write('<OBJECT classid="CLSID:76AB1C26-34A0-4898-A90B-74CCFF435C43" '+ GridReportCodeBase);
	document.write(' width="' + Width + '" height="' + Height + '" id="ReportDesigner" VIEWASTEXT>');
	document.write('<param name="LoadReportURL" value="' + LoadReportURL + '">');
	document.write('<param name="SaveReportURL" value="' + SaveReportURL + '">');
	document.write('<param name="DataURL" value="' + DataURL + '">');
	document.write('<param name="SerialNo" value="' + SerialNo + '">');
	document.write('<param name="UserName" value="' + UserName + '">');
	document.write(ExParams);
	document.write('</OBJECT>');
}
