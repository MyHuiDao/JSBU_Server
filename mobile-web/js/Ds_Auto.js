var DApp={
    app:null,
    isInit:true,
    previousApp:null,
    converTimer:null,
    init:function(maxWidth, maxHeight,whFlag){
        DApp.convertStyle(maxWidth, maxHeight,whFlag);
    },
    convertStyle:function(maxWidth, maxHeight,whFlag){
        if(DApp.app!=null){
            DApp.previousApp=DApp.app;
        }
        DApp.app=new DAppManager(maxWidth, maxHeight,whFlag);
        for(var i=0;i<document.styleSheets.length;i++){
            DApp.app.convertStyle(i);
        }
        if(DApp.isInit){
            DApp.isInit=false;
        }
    }
};
function DAppManager(maxWidth, maxHeight, minWidth, minHeight,whFlag) {
    this.widthScale = 0;
    this.heightScale = 0;
    this.width = 0;
    this.height = 0;
    this.autoWidth={"width":0,"paddingLeft":0,"paddingRight":0,"marginLeft":0,"marginRight":0,"fontSize":0,"left":0,"letterSpacing":0,"right":0};
    this.autoHeight={"height":0,"paddingTop":0,"paddingBottom":0,"marginTop":0,"marginBottom":0,"top":0,"lineHeight":0};
    this.Dinit(maxWidth, maxHeight, minWidth, minHeight,whFlag);
}

DAppManager.prototype.Dinit = function (maxWidth, maxHeight,whFlag) {
    maxWidth=maxWidth==null?document.documentElement.clientWidth:maxWidth;
    maxHeight=maxHeight==null?document.documentElement.clientHeight:maxHeight;
    var scale = maxWidth / maxHeight;
    var clientWidth = document.documentElement.clientWidth > maxWidth ? maxWidth : document.documentElement.clientWidth;
    var clientHeight = document.documentElement.clientHeight > maxHeight ? maxHeight : document.documentElement.clientHeight;
    var resultHeight = parseInt(clientWidth / scale);
    var resultWidth = parseInt(clientHeight * scale);
    if (whFlag=="width") {
        resultWidth = clientWidth; //以宽为主
    } else if (whFlag=="height") {//
        resultHeight = clientHeight; //以高为主
    }else if(resultHeight <= clientHeight){
        resultWidth = clientWidth; //以宽为主
    }else if(resultWidth <= clientWidth){
        resultHeight = clientHeight;//以高为主
    }
    this.width = resultWidth;
    this.height = resultHeight;
    this.widthScale = maxWidth / resultWidth;
    this.heightScale = maxHeight / resultHeight;
};

DAppManager.prototype.toX = function (x) {
    return parseInt((x / this.widthScale * 100) / 100);
};
//转换适合当前坐标y或者h
DAppManager.prototype.toY = function (y) {
    return parseInt((y / this.heightScale * 100) / 100);
};


DAppManager.prototype.convertStyle = function (index) {
    var cssset = document.styleSheets[index].cssRules||document.styleSheets[index].rules;
	if(cssset==null){
	    return;
	}
    for(var i=0;i<cssset.length;i++){
        var style=cssset[i].style;
        for(var key in style){
            if(this.autoWidth[key]==0){
                if(style[key].lastIndexOf("px")!=-1){
                    var strSub = style[key].substring(0, style[key].length - 2);
                    if(!DApp.isInit){
                        strSub=parseInt(strSub*DApp.previousApp.widthScale);
                    }
                    var x=this.toX(Number(strSub));
                    style[key]=x+"px";
                }
                continue;
            }
            if(this.autoHeight[key]==0){
                if(style[key].lastIndexOf("px")!=-1){
                    var strSub = style[key].substring(0, style[key].length - 2);
                    if(!DApp.isInit){
                        strSub=parseInt(strSub*DApp.previousApp.heightScale);
                    }
                    var y=this.toY(Number(strSub));
                    style[key]=y+"px";
                }
            }
        }
    }
};
