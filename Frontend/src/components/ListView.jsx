import React, { useState, useEffect } from 'react';
import AppointmentBox from './AppointmentBox';

function ListView() {
    
    const [data, setData] = useState([{title: "title", content: "content", no: "1"}]);
    
    // const NewsRow = (props) => {
    //     const title = props.row.title;
    //     const content = props.row.content;

    //     return (
    //         <li>
    //             <div className="title">
    //                 <span>{title}</span>
    //             </div>
    //             <div className="content">
    //                 <span>{content}</span>
    //             </div>
    //         </li>
    //     );
    // };
    
    useEffect(() => {
        // let apiUrl = 'https://openapi.naver.com/v1/search/news?query=올림픽';
        // axios.get(apiUrl, {
        //     headers: {
        //         'Content-Type': 'application/json',
        //         'X-Naver-Client-Id': NAVER_CLIENT_ID,
        //         'X-Naver-Client-Secret': NAVER_CLIENT_SECRET
        //     }
        // })
        // .then(({data}) => {
        //     setArticles(data.items);
        // })
        // .catch(e => {
        //     console.error(e.stack);
        // });
        

        setData(data => [...data, {
            title: "title2", content: "content2", no: "2"
        }]);
    }, []);
    
    return (
        <ul className='listView'>
        {
            // <label>test2</label>
            // data && data.map((value, idx) => {
            //     return <NewsRow key={idx} row={value} />
            // })
            data.map((value, idx) => {
                return <AppointmentBox key={idx} row={value} />
            })
        }
        </ul>
    );
};

export default ListView;