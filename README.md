 نظام المواعيد الذكي - Smart Appointment System
 1. شرح عام عن المشروع
تطبيق ويب متكامل لإدارة وحجز المواعيد مبني باستخدام إطار العمل Spring Boot يتميز النظام ببيئة آمنة تعتمد على JWT و HTTPS مع ميزة التحديثات اللحظية عبر WebSockets كما يدمج التطبيق تقنيات الذكاء الاصطناعي من خلال Gemini AI Service لتحليل ومعالجة طلبات المستخدمين.

 2. تقسيم العمل بين الطلاب
*فاطمة محمد جمعة كربوج | عمار عماد تركمان | محمد انس سعد الدين جاويش  :AI (Gemini Service) , برمجة الـ Backend، الـ Controllers الأساسية، وإعداد قاعدة بيانات H2.
* المعتصم بالله خالد عليان | مصطفى جلال غانم : إعداد طبقة الأمان (Spring Security)، الـ JWT Authentication، والـ SSL (HTTPS) و إعداد الـ WebSocket للتواصل اللحظي وربط خدمة الـ .
              

 3. كيفية تشغيل المشروع
1. تأكد من توفر **JDK 17** أو أحدث.
2. المشروع يعتمد على مستودع **Maven**؛ قم بتحميل المكتبات عبر ملف `pom.xml`.
3. المشروع مضبوط للعمل على المنفذ **8443** (عبر بروتوكول HTTPS).
4. لتشغيل المشروع من سطر الأوامر:
   ```bash
   mvn spring-boot:run


  4. الـ APIs الموجودة في المشروع
| المسار (Endpoint) | الطريقة (Method) | الوصف |
| `api/auth/signup` | POST | انشاء حساب في الموقع  |
| `/api/auth/login` | POST | التحقق من المستخدم وتوليد توكن JWT |
| `/api/services/all` | GET | جلب قائمة المواعيد الخاصة بالمستخدم |
| `/api/services/add` | POST | إضافة خدمة الى قائمة  الخاصة  |
| `/api/services/delete/{id}` | delete | حذف خدمة  |
| `/api/services/update/{id}` | put | تعديل على الخدمة  |
| `/api/appointments/{id}/approve` | put |  الموافقة على الموعد   |
| /api/appointments/{id}/reject` | put |رفض الموعد  |
| `/api/appointments/book` | POST | حجز موعد جديد في النظام |
| `/api/ai/suggest` | POST | إرسال طلب لمعالجة البيانات عبر Gemini AI |
| `/ws-endpoint` | WebSocket | نقطة الاتصال اللحظي لتحديثات النظام |
https://localhost:8443/index.html      صفحة الاشعارات 
https://localhost:8443/h2-console      الدتا بيز h2
