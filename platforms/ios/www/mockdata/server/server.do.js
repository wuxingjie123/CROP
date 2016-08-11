define({
    "act=HelloWorld": [
        {
            isDefault: false,
            delay: 500,
            strictMode: true,
            data: {
                HelloString: "Hello World !"
            }
        },
        {
            delay: 200,
            result: 0,
            param: {
                name: "citic"
            },
            data: {
                HelloString: "Hello Citic !"
            }
        },
        {
            data: {
                err: "模拟出错信息。"
            }
        }
    ],
    "act=getList": [
        {
            delay: 1000,
            strictMode:true,
            data: {
                data: [
                    {
                        text: "001",
                        type:'person',
                        children:[
                            {text:"003"}
                        ]
                    },
                    {
                        text: "002",
                        type:'person'
                    }
                ]
            }
        }
    ],
    "act=getSelect": [
        {
            data: {
                selected: 0,
                items: [
                    {title: "选项A", value: "v1"},
                    {title: "选项B", value: "v2"}
                ]}
        }
    ],
	"act=header":[
        {
            data:{
                result:[
                    {classname:'h_icon1',pathname:'guidecommenquestions',titlename:'帮助中心'},
                    {classname:'h_icon5',pathname:'indexhtml',titlename:'首页'},
                    {classname:'h_icon7',pathname:'bankeciticcom',titlename:'中信银行'}
                ]
            }
        }
    ]
});