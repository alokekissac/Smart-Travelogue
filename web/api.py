from flask import *
from database import *

import uuid


api=Blueprint('api',__name__)



@api.route('/login', methods=['post','get'])
def login():
	username = request.form['uname']
	password = request.form['password']
	print(username)
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	print(res)
	if res:
		return jsonify(status="success", lid=res[0]['login_id'], type=res[0]["usertype"])
	else:
		return jsonify(status="failure")

@api.route('/user_registration',methods=['post','get'])
def user_registration():
	data={}
	fname=request.form['fname']
	lname=request.form['lname']
	phone=request.form['phone']
	address=request.form['address']
	place=request.form['place']
	pin=request.form['pin']
	email=request.form['email']
	passw=request.form['passw']
	qr="SELECT * FROM `login` WHERE `username`='%s' OR `password`='%s'"%(email,passw)
	res=select(qr)
	if res:
		data['status']='duplicate'
		return jsonify(status="duplicate")
	else:
		q="INSERT INTO `login` VALUES(NULL,'%s','%s','user')"%(email,passw)
		val=insert(q)
		qr="INSERT INTO `users` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','0','0')"%(val,fname,lname,address,pin,place,phone,email)
		insert(qr)
		return jsonify(status="success")

@api.route('/User_view_places', methods=['post','get'])
def User_view_places():
    q = "SELECT * FROM `place_type` INNER JOIN `places` ON `place_type`.`place_type_id`=`places`.`type_id`"
    res = select(q)
    print(q)
    return jsonify(status="ok", data=res)


@api.route('/useradd_to_fever', methods=['post','get'])
def useradd_to_fever():
    data = {}
    lid = request.form['lid']
    place_id = request.form['place_id']

    # Check if the place is already a favorite for the user
    query = "SELECT * FROM `interests` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') AND `place_id`='%s'" % (lid, place_id)
    res = select(query)

    if res:
        # Place is already a favorite
        data['status'] = "color is permenet"
    else:
        # Place is not a favorite, add it to favorites
        insert_query = "INSERT INTO `interests` VALUES (NULL, (SELECT `user_id` FROM `users` WHERE `login_id`='%s'), '%s')" % (lid, place_id)
        insert(insert_query)
        data['status'] = 'success'

    return jsonify(data)

@api.route('/userremove_from_fever', methods=['post','get'])
def userremove_from_fever():
    data = {}
    lid = request.form['lid']
    place_id = request.form['place_id']
    query = "DELETE FROM `interests` WHERE `user_id` = (SELECT `user_id` FROM `users` WHERE `login_id`='%s') AND `place_id` = '%s'"%(lid,place_id)
    delete(query)
    data['status'] = 'success'
    return jsonify(data)



@api.route('/User_view_hotels', methods=['post','get'])
def User_view_hotels():
    q = "SELECT * FROM `hotels`"
    res = select(q)
    print(q)
    return jsonify(status="ok", data=res)

@api.route('/User_view_hotelpackeges', methods=['post','get'])
def User_view_hotelpackeges():
	hotel_id = request.form['hotel_id']
	q = "SELECT * FROM `hotels` INNER JOIN `packages` USING(`hotel_id`) WHERE `hotel_id`='%s'"%(hotel_id)

	res = select(q)
	print(q)
	return jsonify(status="ok", data=res)




@api.route('/usersendfeedback',methods=['post','get'])
def usersendfeedback():
	data={}
	login_id=request.form['lid']
	feed_des=request.form['feedback']
	title=request.form['description']
	q= "INSERT INTO `feedback` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','%s',NOW())"% (login_id,title,feed_des)
	print(q)
	id=insert(q)
	return jsonify(status="success")

@api.route('/userviewfeedback',methods=['post','get'])
def userviewfeedback():
	data = {}

	log_id=request.form['lid']
	
	q="SELECT * FROM `feedback` WHERE `login_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	result = select(q)
	return jsonify(status="ok", data=result)



@api.route('/user_view_noti',methods=['post','get'])
def user_view_noti():
	data = {}	
	qr="SELECT * FROM `notifications`"
	result = select(qr)
	return jsonify(status="ok", data=result)


@api.route('/Customer_send_complaint',methods=['post','get'])
def Customer_send_complaint():
    data={}
    log_id=request.form['lid']
    complaint=request.form['complaint']
    q="INSERT INTO `complaints` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','pending',curdate())"%(log_id,complaint)
    print(q)
    res=insert(q)
    if res:
        return jsonify(status="success")
    else:
    	return jsonify(status="failed")
       

@api.route('/view_complaint',methods=['post','get'])
def view_complaint():
    data={}
    log_id=request.form['lid']
    q="SELECT * FROM `complaints` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
    res=select(q)
    if res:
        return jsonify(status="ok", data=res)
    else:
        return jsonify(status="nodata")









@api.route('/user_add_travelogue',methods=['post','get'])
def user_add_travelogue():
	data={}
	loginid=request.form['lid']
	p_name=request.form['place']
	title=request.form['title']
	des=request.form['description']
	q= "INSERT INTO `travelogue` VALUES(NULL,'%s','%s','%s','%s',NOW())"%(loginid,p_name,title,des)
	print(q)
	id=insert(q)
	return jsonify(status="success")


@api.route('/user_view_travelogue',methods=['post','get'])
def user_view_travelogue():
	data = {}
	loginid=request.form['lid']
	qr="SELECT * FROM `travelogue` WHERE `login_id`='%s'"%(loginid)
	result = select(qr)
	return jsonify(status="ok", data=result)



@api.route('/deletetravelogue', methods=['post','get'])
def deletetravelogue():
    data = {}
    travelogue_id = request.form['travelogue_id']
    query = "DELETE FROM `travelogue` WHERE `travelogue_id`='%s'"%(travelogue_id)
    delete(query)
    qr="DELETE FROM `uploads` WHERE `travelogue_id`='%s'"%(travelogue_id)
    delete(qr)
    data['status'] = 'success'
    return jsonify(data)


@api.route('/user_upload_file',methods=['post','get'])
def user_upload_file():

	data={}
	path = ""
	trv_id=request.form['travelogue_id']
	yts=request.form['yts']
	desc=request.form['desc']
	logid=request.form['lid']
	image=request.files['file']
	ftype = request.form['ftype']

	if ftype == 'video':
		path=save_upload(image,str(uuid.uuid4())+".mp4")
	else:
		path=save_upload(image,str(uuid.uuid4())+".jpg")
	q="INSERT INTO `uploads` VALUES(NULL,'%s','%s','%s','%s','%s','%s')"%(trv_id,logid,path,yts,desc,ftype)
	print(q)
	res=insert(q)
	if res:
		return jsonify(status="success")
	else:
		return jsonify(status="failure")


@api.route('/myuser_view_videos', methods=['post','get'])
def myuser_view_videos():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='video' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)

@api.route('/myuser_delete_videos', methods=['post','get'])
def myuser_delete_videos():
	data={}
	upload_id=request.form['upload_id']
	qr="DELETE FROM `uploads` WHERE `upload_id`='%s'"%(upload_id)
	delete(qr)
	return jsonify(status="ok")



@api.route('/myUser_view_travel_images', methods=['post','get'])
def myUser_view_travel_images():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='image' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)


@api.route('/myuser_delete_images', methods=['post','get'])
def myuser_delete_images():
	data={}
	upload_id=request.form['upload_id']
	qr="DELETE FROM `uploads` WHERE `upload_id`='%s'"%(upload_id)
	delete(qr)
	return jsonify(status="ok")



@api.route('/My_view_travelogue',methods=['post','get'])
def My_view_travelogue():
	data = {}
	loginid=request.form['lid']
	qr="SELECT * FROM `travelogue` WHERE `login_id`='%s'"%(loginid)
	result = select(qr)
	return jsonify(status="ok", data=result)


@api.route('/user_view_images_videos', methods=['post','get'])
def user_view_images_videos():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='video' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)


@api.route('/User_view_travel_images', methods=['post','get'])
def User_view_travel_images():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='image' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)


@api.route('/UserViewOthersTravelogue',methods=['post','get'])
def UserViewOthersTravelogue():
	data = {}
	loginid=request.form['lid']
	qr="SELECT * FROM `travelogue` WHERE `login_id` <> '%s'"%(loginid)
	result = select(qr)
	return jsonify(status="ok", data=result)




@api.route('/PublicViewOthersTravelogue',methods=['post','get'])
def PublicViewOthersTravelogue():
	data = {}
	loginid=request.form['lid']
	qr="SELECT * FROM `travelogue` "
	result = select(qr)
	return jsonify(status="ok", data=result)




@api.route('/PublicviewTravelVideos', methods=['post','get'])
def PublicviewTravelVideos():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='video' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)


@api.route('/PublicviewTravelImages', methods=['post','get'])
def PublicviewTravelImages():
    data = {}
    trav_id = request.form['travelogueId']
    # qr = "SELECT * FROM `uploads` WHERE `travelogue_id`='%s'" % (trav_id)
    qr = "SELECT * FROM `uploads` where upload_type='image' and `travelogue_id`='%s'"%(trav_id)
    print(qr)
    result = select(qr)
    return jsonify(status="ok", data=result)

