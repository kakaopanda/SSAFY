<template>
  <div>
    <div id="map">
    <div class="map_wrap">
        <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
        <ul id="category">
            <li id="BK9" data-order="0"> 
                <span class="category_bg bank"></span>
                은행
            </li>       
            <li id="MT1" data-order="1"> 
                <span class="category_bg mart"></span>
                마트
            </li>  
            <li id="PM9" data-order="2"> 
                <span class="category_bg pharmacy"></span>
                약국
            </li>  
            <li id="OL7" data-order="3"> 
                <span class="category_bg oil"></span>
                주유소
            </li>  
            <li id="CE7" data-order="4"> 
                <span class="category_bg cafe"></span>
                카페
            </li>  
            <li id="CS2" data-order="5"> 
                <span class="category_bg store"></span>
                편의점
            </li>      
        </ul>
    </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "KakaoMap",
  data() {
    return {
      markers: [],
      infowindow: null,
      houses: null,

      placeOverlay: null,
      contentNode: null,
      currCategory: '',
      ps: null, 
    };
  },
  computed: {
    check_info(){
      return this.$store.state.houseStore.houses;
    }
  },
  watch: {
      check_info(val){
      this.houses = val;
      let markerInfo = [];
      let latSet = new Set();
      let lngSet = new Set();

      for(var i=0; i<this.houses.length; i++){
        markerInfo.push([this.houses[i].lat, this.houses[i].lng]);
        latSet.add(this.houses[i].lat);
        lngSet.add(this.houses[i].lng);
      }

      let latArray = Array.from(latSet);
      let lngArray = Array.from(lngSet);

      let uniqueInfo = [];
      for(var j=0; j<latArray.length; j++){
        uniqueInfo.push([latArray[j],lngArray[j]]);
      }
      this.displayMarker(uniqueInfo);
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    // 주변 상권 정보 Start.
    addCategoryClickEvent() {
      var category = document.getElementById('category'),
          children = category.children;

      for (var i=0; i<children.length; i++) {
          children[i].onclick = this.onClickCategory;
      }
    },
    onClickCategory() {
      console.log(document.getAttribute("id"));
        var id = this.id,
        className = this.className;

        this.placeOverlay.setMap(null);
        console.log(id+","+this.className);
        if (className === 'on') {
            this.currCategory = '';
            this.changeCategoryClass();
            this.removeMarker();
        } else {
            this.currCategory = id;
            this.changeCategoryClass(this);
            this.searchPlaces();
        }
    },
    changeCategoryClass(el) {
        var category = document.getElementById('category'),
            children = category.children,
            i;

        for ( i=0; i<children.length; i++ ) {
            children[i].className = '';
        }

        if (el) {
            el.className = 'on';
        } 
    },
    removeMarker() {
        console.log("removeMarker()");
        for ( var i = 0; i < this.markers.length; i++ ) {
            this.markers[i].setMap(null);
        }   
        this.markers = [];
    },
    searchPlaces() {
      console.log("searchPlaces()");
      console.log(this.currCategory);
        if (!this.currCategory) {
            return;
        }
        
        // 커스텀 오버레이를 숨깁니다 
        this.placeOverlay.setMap(null);

        // 지도에 표시되고 있는 마커를 제거합니다
        console.log("저기까지는 와요!!");
        this.removeMarker();
        console.log("여기까지는 와요!!");
        this.ps.categorySearch(this.currCategory, this.placesSearchCB, {useMapBounds:true}); 
    },
    placesSearchCB(data, status) {
        console.log("placesSearchDB()");
        if (status === kakao.maps.services.Status.OK) {
            // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
            console.log("success");
            this.displayPlaces(data);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요

        } else if (status === kakao.maps.services.Status.ERROR) {
            // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
            
        }
    },
    displayPlaces(places) {
        console.log("displayPlaces()");
        // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
        // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
        var order = document.getElementById(this.currCategory).getAttribute('data-order');

        

        for ( var i=0; i<places.length; i++ ) {

                // 마커를 생성하고 지도에 표시합니다
                var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

                // 마커와 검색결과 항목을 클릭 했을 때
                // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
                (function(marker, place) {
                    kakao.maps.event.addListener(marker, 'click', function() {
                        this.displayPlaceInfo(place);
                    });
                })(marker, places[i]);
        }
    },
    displayPlaceInfo (place) {
        var content = '<div class="placeinfo">' +
                        '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';   

        if (place.road_address_name) {
            content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
                        '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
        }  else {
            content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
        }                
      
        content += '    <span class="tel">' + place.phone + '</span>' + 
                    '</div>' + 
                    '<div class="after"></div>';

        this.contentNode.innerHTML = content;
        this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
        this.placeOverlay.setMap(this.map);  
    },
    addMarker(position, order) {
        var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
            imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
            imgOptions =  {
                spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
                spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
                marker = new kakao.maps.Marker({
                position: position, // 마커의 위치
                image: markerImage 
            });

        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다

        return marker;
    },
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
          target.addEventListener(type, callback);
      } else {
          target.attachEvent('on' + type, callback);
      }
    },
    // 주변 상권 정보 End.

    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      this.placeOverlay =  new kakao.maps.CustomOverlay({zIndex:1});
      this.contentNode = document.createElement('div');
      this.ps = new kakao.maps.services.Places(this.map);

      kakao.maps.event.addListener(this.map, 'idle', this.searchPlaces);
      this.contentNode.className = 'placeinfo_wrap';

      this.addEventHandle(this.contentNode, 'mousedown', kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, 'touchstart', kakao.maps.event.preventMap);

      this.placeOverlay.setContent(this.contentNode);
      this.addCategoryClickEvent();
    },
    changeSize(size) {
      const container = document.getElementById("map");
      container.style.width = `${size}px`;
      container.style.height = `${size}px`;
      this.map.relayout();
    },
    displayMarker(markerPositions) {
      // console.log(markerPositions);
      // console.log(this.$store.state.houseStore);
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  border: 1px solid #DDDDDD;
  width: 49%;
  height: 89%;
  position:absolute;
  left:15px;
  top:115px;
  bottom:15px;
  margin:0px;
  padding:0;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}

.map_wrap, .map_wrap * {margin:0; padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap {position:relative;width:100%;height:350px;}
#category {position:absolute;top:10px;left:10px;border-radius: 5px; border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);background: #fff;overflow: hidden;z-index: 2;}
#category li {float:left;list-style: none;width:50px; border-right:1px solid #acacac;padding:6px 0;text-align: center; cursor: pointer;}
#category li.on {background: #eee;}
#category li:hover {background: #ffe6e6;border-left:1px solid #acacac;margin-left: -1px;}
#category li:last-child{margin-right:0;border-right:0;}
#category li span {display: block;margin:0 auto 3px;width:27px;height: 28px;}
#category li .category_bg {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;}
#category li .bank {background-position: -10px 0;}
#category li .mart {background-position: -10px -36px;}
#category li .pharmacy {background-position: -10px -72px;}
#category li .oil {background-position: -10px -108px;}
#category li .cafe {background-position: -10px -144px;}
#category li .store {background-position: -10px -180px;}
#category li.on .category_bg {background-position-x:-46px;}
.placeinfo_wrap {position:absolute;bottom:28px;left:-150px;width:300px;}
.placeinfo {position:relative;width:100%;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;padding-bottom: 10px;background: #fff;}
.placeinfo:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
.placeinfo_wrap .after {content:'';position:relative;margin-left:-12px;left:50%;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
.placeinfo a, .placeinfo a:hover, .placeinfo a:active{color:#fff;text-decoration: none;}
.placeinfo a, .placeinfo span {display: block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
.placeinfo span {margin:5px 5px 0 5px;cursor: default;font-size:13px;}
.placeinfo .title {font-weight: bold; font-size:14px;border-radius: 6px 6px 0 0;margin: -1px -1px 0 -1px;padding:10px; color: #fff;background: #d95050;background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
.placeinfo .tel {color:#0f7833;}
.placeinfo .jibun {color:#999;font-size:11px;margin-top:0;}
</style>
