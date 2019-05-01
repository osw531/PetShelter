<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zxx" class="no-js">
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon" href="img/fav.png">
<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>Animal Shelter</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--
			CSS
			============================================= -->
<link rel="stylesheet" href="css/linearicons.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/nice-select.css">
<link rel="stylesheet" href="css/animate.min.css">
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/main.css">
<style>
#googleMap{
	text-align:center;
	margin:auto;
}
.banner-areawon {
  background: url(/user/img/dog1.jpg) center;
  background-size: cover;
}

.banner-areawon .primary-btn {
  padding-left: 30px;
  padding-right: 30px;
}

.banner-areawon .overlay-bg {
  background-color: rgba(0, 0, 0, 0.4);
}
</style>
<script>
		function myMap() {
			var latLng = new google.maps.LatLng(37.571066, 126.992255);
			var mapProp = {
				center : latLng,
				zoom : 18,
			};
			var map = new google.maps.Map(document.getElementById("googleMap"),
					mapProp);
			var marker = new google.maps.Marker({
				position: latLng,
				animation:google.maps.Animation.BOUNCE,
				icon:"/user/img/dogmarker.png"
				});

			marker.setMap(map); //맵에 마커 등록!!!
			var infowindow = new google.maps.InfoWindow(
					{
						content : "이곳이 <h3 style='color:red'><img src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaC0GwlHHA2DidMj50Vec2SR4IUaj_nUW1lgBTppttSiSv386dxQ'></h3>이다!"
					});

			// Zoom to 9 when clicking on marker
			google.maps.event.addListener(marker, 'click', function() {
				map.setCenter(marker.getPosition());
				infowindow.open(map, marker);
				var pos = map.getZoom(); //기존에 설정된 줌의 중심점!!
				map.setZoom(15);
				//일정 시간뒤에 무언가 하고 싶을 때
				window.setTimeout(function() {
					map.setZoom(pos);
				}, 2400);
			});

		}
	</script>
	
</head>
<body>
 <%@include file="/user/inc/header.jsp" %>
	<!-- start banner Area -->
	<section class="banner-areawon relative" id="home">
		<div class="overlay overlay-bg"></div>
		<div class="container">
			<div class="row d-flex align-items-center justify-content-center">
				<div class="about-content col-lg-12">
					<h1 class="text-white">About Us</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- End banner Area -->
	<br>
	<!-- Start home-about Area -->
	<section class="home-about-area">
		<div class="container-fluid">
			<div class="row align-items-center">
				<div class="col-lg-6 home-about-left no-padding">
					<img src="img/about-img.jpg" alt="" style="width: 70%">
				</div>
				<div class="col-lg-6 home-about-right no-padding">
					<h1></h1>
					<h4 class="mb-10">‘잠시 집을 잃은 아이’에게 손을 내밀어 보세요. 
					<br>
					나로 인해 한 생명의 삶이 바뀌고, 
					<br>
					곧 내 삶에 기분 좋은 변화가 생기게 됩니다.
					<br>
					가정에서 임시보호 중인 유기견 정보 제공 및 집을 잃은 아이들을 임시 보호하고 있습니다. 
					<br>
					강아지 입양에 어려운 점이나 고민이 있으면 글을 남겨주세요.<br> 
					전문상담사가 항시 대기 중입니다 🙂</h4>
					<p></p>
				</div>
			</div>
		</div>
	</section>
	<!-- End home-about Area -->

	<!-- Start testomial Area -->
	<section class="testomial-area section-gap" id="testimonail">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="menu-content pb-60 col-lg-8">
					<div class="title text-center">
						<h1 class="mb-10">관리자 소개</h1>
						<p>강아지를 사랑하는 센터 관리자들을 소개합니다</p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="active-testimonial-carusel">
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/min.png" alt="">
						<p class="desc">변함없는 사랑으로 아이들을 아끼며 ArounDog 센터 관리 뿐만 아니라 자선 봉사활동, 동물 보호협회, 유기견 공인 보호센터와 협력하여
						집을 잃은 아이들을 보살피기 위해 헌신하고 있습니다. 
						<br>
						10년이 넘는 시간 동안 센터를 운영하고 있으며, 아이들의 부모로써 책임을 다하고 있습니다. 						
						<h4>Kim Min Ho</h4>
						<p>CEO at Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/kim.png" alt="">
						<p class="desc">She loves dogs and is always interested in
							problems with abandoned dogs, and she is a responsible manager.
							As a manager, She is doing her best for the puppies at the center.
						<h4>Kim Hyeon Hwa</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/kwon.png" alt="">
						<p class="desc">She loves dogs and is always interested in
							problems with abandoned dogs, and she is a responsible manager.
							As a manager, She is doing her best for the puppies at the center.
						<h4>Kwon Ji Young</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/dong.png" alt="">
						<p class="desc">He loves dogs and is always interested in
							problems with abandoned dogs, and he is a responsible manager. As
							a manager, He is doing his best for the puppies at the center.
						<h4>Hwang Dong Hyeon</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/sewon.png" alt="">
						<p class="desc">He loves dogs and is always interested in
							problems with abandoned dogs, and he is a responsible manager. As
							a manager, He is doing his best for the puppies at the center.
						<h4>Oh Se Won</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/zino1187.png" alt="">
						<p class="desc">The God of Coding</p>
						<h4>Min Zino</h4>
						<p>CEO at minssam.com</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End testomial Area -->
	<!-- 구글 지도 -->
	<div id="googleMap" style="width: 70%; height: 500px;"></div>
	<br>
	<br>
	<!-- start footer Area -->
	<%@include file="/user/inc/footer.jsp" %>
	<!-- End footer Area -->
	<%@include file="/user/inc/tail.jsp" %>
</body>
</html>