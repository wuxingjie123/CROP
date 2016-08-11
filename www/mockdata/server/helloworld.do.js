define({
    "act=getAllData": [
        {
            delay: 1000,
            data: {
                labelData: 'Label',
                selectData: {
                    value: 0,
                    select: [
                        {option: '1|500'},
                        {option: '2|600'}
                    ]
                },
                listData: {
                    list: [
                        {id: '1', title: 500},
                        {id: '2', title: 600}
                    ]
                }
            }
        }
    ],
    "act=getAllData2": [
        {
            delay: 1000,
            data: {
                mylabel: 'Label',
                myselect: {
                    select: [
                        {option: '1|500'},
                        {option: '2|600'}
                    ]},
                mylist: {
                    list: [
                        {id: '1', title: 500},
                        {id: '2', title: 600}
                    ]
                },
                mypopupselect: {
                    showCancel:true,
                    items: [
                        {id: '1', title: 5000},
                        {id: '2', title: 6000}
                    ]
                }
            }
        }
    ],
    "act=getText": [
        {
            delay: 1000,
            data: {
                TEXT: "Hello World !"
            }
        }
    ],
    "act=getSelect": [
        {
            delay: 1000,
            data: {
                selected: 0,
                select: [
                    {option: '1|500'},
                    {option: '2|600'}
                ]
            }
        }
    ],
    "act=getPopupSelect": [
        {
            delay: 1000,
            data: {
                items: [
                    {id: '1', title: 500},
                    {id: '2', title: 600}
                ]
            }
        }
    ],
    "act=getList": [
        {
            delay: 1000,
            param:{
              page:1
            },
            data: {
                list: [
                    {id: '1', title: 500}
                ],
                SUMCOUNT:5
            }
        },
        {
            delay: 1000,
            param:{
                page:2
            },
            data: {
                list: [
                    {id: '2', title: 600}
                ],
                SUMCOUNT:5
            }
        }
    ],
    "act=getList2": [
        {
            delay: 1000,
            data: {
                mylist: [
                    {id: '1', title: 500},
                    {id: '2', title: 600}
                ]
            }
        }
    ],
    "act=getSecondText": [
        {
            isDefault: false,
            delay: 1000,
            data: {
                TEXT1: "Hello Second World A!",
                TEXT2: "Hello Second World B!"
            }
        },
        {
            delay: 2000,
            result: 0,
            param: {
                USERNAME: "citic",
                PASSWORD: "112233"
            },
            data: {
                TEXT1: "Hello Citic A!",
                TEXT2: "Hello Citic B!"
            }
        },
        {
            data: {
                err: "模拟出错信息。"
            }
        }
    ]
});