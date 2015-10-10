var adrsInit=function(_cmbProv,_cmbCity,_cmbArea,dProv,dcity,dArea){
	var cmbProv=document.getElementById(_cmbProv);
	var cmbCity=document.getElementById(_cmbCity);
	var cmbArea=document.getElementById(_cmbArea);
	cmbProv.innerHTML='';
	cmbCity.innerHTML='';
	cmbArea.innerHTML='';
	function cmbslc(cmb,str){
		for(var i=0;i<cmb.options.length;i++){
			if(cmb.options[i].value==str){
				cmb.selectedIndex=i;return
			}
		}
	}
	function cmbAddOpt(cmb,str,obj){
		var option=document.createElement("OPTION");
		cmb.options.add(option);
		option.innerHTML=str;
		option.value=str;
		option.obj=obj
	}
	function changec(){
		cmbArea.options.length=0;
		if(cmbCity.selectedIndex==-1)return;
		var item=cmbCity.options[cmbCity.selectedIndex].obj;
		for(var i=0;i<item.a.length;i++){
			cmbAddOpt(cmbArea,item.a[i],null)
		}
		cmbslc(cmbArea,dArea)
	}
	function changeProv(){
		cmbCity.options.length=0;
		cmbCity.onchange=null;
		if(cmbProv.selectedIndex==-1)return;
		var item=cmbProv.options[cmbProv.selectedIndex].obj;
		for(var i=0;i<item.c.length;i++){
			cmbAddOpt(cmbCity,item.c[i].n,item.c[i])
		}
		cmbslc(cmbCity,dcity);changec();
		cmbCity.onchange=changec
	}
	for(var i=0;i<provList.length;i++){
		cmbAddOpt(cmbProv,provList[i].n,provList[i])
	}
	cmbslc(cmbProv,dProv);
	changeProv();
	cmbProv.onchange=changeProv
};
var provList =[{n:'江苏省',c:[{n:'南京市',a:['玄武区','白下区','秦淮区','建邺区','鼓楼区','下关区','浦口区','栖霞区','雨花台区','江宁区','六合区','溧水县','高淳县']},{n:'无锡市',a:['崇安区','南长区','北塘区','锡山区','惠山区','滨湖区','江阴市','宜兴市']},{n:'徐州市',a:['鼓楼区','云龙区','贾汪区','泉山区','铜山区','丰县','沛县','睢宁县','新沂市','邳州市']},{n:'常州市',a:['天宁区','钟楼区','戚墅堰区','新北区','武进区','溧阳市','金坛市']},{n:'苏州市',a:['虎丘区','吴中区','相城区','姑苏区','吴江区','常熟市','张家港市','昆山市','太仓市']},{n:'南通市',a:['崇川区','港闸区','通州区','海安县','如东县','启东市','如皋市','海门市']},{n:'连云港市',a:['连云区','新浦区','海州区','赣榆县','东海县','灌云县','灌南县']},{n:'淮安市',a:['清河区','淮安区','淮阴区','清浦区','涟水县','洪泽县','盱眙县','金湖县']},{n:'盐城市',a:['亭湖区','盐都区','响水县','滨海县','阜宁县','射阳县','建湖县','东台市','大丰市']},{n:'扬州市',a:['广陵区','邗江区','江都区','宝应县','仪征市','高邮市']},{n:'镇江市',a:['京口区','润州区','丹徒区','丹阳市','扬中市','句容市']},{n:'泰州市',a:['海陵区','高港区','兴化市','靖江市','泰兴市','姜堰市']},{n:'宿迁市',a:['宿城区','宿豫区','沭阳县','泗阳县','泗洪县']}]},{n:'北京市',c:[{n:'市辖区',a:['东城区','西城区','朝阳区','丰台区','石景山区','海淀区','门头沟区','房山区','通州区','顺义区','昌平区','大兴区','怀柔区','平谷区']},{n:'县',a:['密云县','延庆县']}]},{n:'天津市',c:[{n:'市辖区',a:['和平区','河东区','河西区','南开区','河北区','红桥区','东丽区','西青区','津南区','北辰区','武清区','宝坻区','滨海新区']},{n:'县',a:['宁河县','静海县','蓟县']}]},{n:'河北省',c:[{n:'张家口市',a:['桥东区','桥西区','宣化区','下花园区','宣化县','张北县','康保县','沽源县','尚义县','蔚县','阳原县','怀安县','万全县','怀来县','涿鹿县','赤城县','崇礼县']},{n:'石家庄市',a:['长安区','桥东区','桥西区','新华区','井陉矿区','裕华区','井陉县','正定县','栾城县','行唐县','灵寿县','高邑县','深泽县','赞皇县','无极县','平山县','元氏县','赵县','辛集市','藁城市','晋州市','新乐市','鹿泉市']},{n:'唐山市',a:['路南区','路北区','古冶区','开平区','丰南区','丰润区','曹妃甸区','滦县','滦南县','乐亭县','迁西县','玉田县','遵化市','迁安市']},{n:'秦皇岛市',a:['海港区','山海关区','北戴河区','青龙满族自治县','昌黎县','抚宁县','卢龙县']},{n:'邯郸市',a:['邯山区','丛台区','复兴区','峰峰矿区','邯郸县','临漳县','成安县','大名县','涉县','磁县','肥乡县','永年县','邱县','鸡泽县','广平县','馆陶县','魏县','曲周县','武安市']},{n:'邢台市',a:['桥东区','桥西区','邢台县','临城县','内丘县','柏乡县','隆尧县','任县','南和县','宁晋县','巨鹿县','新河县','广宗县','平乡县','威县','清河县','临西县','南宫市','沙河市']},{n:'保定市',a:['新市区','北市区','南市区','满城县','清苑县','涞水县','阜平县','徐水县','定兴县','唐县','高阳县','容城县','涞源县','望都县','安新县','易县','曲阳县','蠡县','顺平县','博野县','雄县','涿州市','定州市','安国市','高碑店市']},{n:'承德市',a:['双桥区','双滦区','鹰手营子矿区','承德县','兴隆县','平泉县','滦平县','隆化县','丰宁满族自治县','宽城满族自治县','围场满族蒙古族自治县']},{n:'沧州市',a:['新华区','运河区','沧县','青县','东光县','海兴县','盐山县','肃宁县','南皮县','吴桥县','献县','孟村回族自治县','泊头市','任丘市','黄骅市','河间市']},{n:'廊坊市',a:['安次区','广阳区','固安县','永清县','香河县','大城县','文安县','大厂回族自治县','霸州市','三河市']},{n:'衡水市',a:['桃城区','枣强县','武邑县','武强县','饶阳县','安平县','故城县','景县','阜城县','冀州市','深州市']}]}];