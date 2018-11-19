<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 18/11/2018
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<!DOCTYPE html>
<html lang="${language}">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ShoppingLesto | Privacy Policy - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 col-lg-3">
        </div>
        <div class="col-lg-6 col-md-8 col-12">
            <c:choose>
                <c:when test="${language eq 'it'}">
                    <h1>Informativa sulla Privacy</h1>


                    <p>Data di entrata in vigore: November 18, 2018</p>


                    <p>ShoppingLesto ("noi" o "nostro") gestisce il shoppinglesto.alessandrogerevini.com sito web (in appresso il "Servizio").</p>

                    <p>Questa pagina vi informa delle nostre politiche riguardanti la raccolta, l'uso e la divulgazione dei dati personali quando usate il nostro Servizio e le scelte che avete associato a quei dati. <a href="https://privacypolicies.com/free-privacy-policy-generator/">Informativa sulla Privacy via Privacy Policies</a>.</p>

                    <p>Utilizziamo i vostri dati per fornire e migliorare il Servizio. Utilizzando il Servizio, accettate la raccolta e l'utilizzo delle informazioni in conformità con questa informativa. Se non diversamente definito nella presente Informativa sulla privacy, i termini utilizzati nella presente Informativa hanno la stessa valenza dei nostri Termini e condizioni, accessibili da shoppinglesto.alessandrogerevini.com</p>

                    <h2>Definizioni</h2>
                    <ul>
                        <li>
                            <p><strong>Servizio</strong></p>
                            <p>Il Servizio è il sito shoppinglesto.alessandrogerevini.com gestito da ShoppingLesto</p>
                        </li>
                        <li>
                            <p><strong>Dati personali</strong></p>
                            <p>I Dati personali sono i dati di un individuo vivente che può essere identificato da quei dati (o da quelli e altre informazioni in nostro possesso o che potrebbero venire in nostro possesso).</p>
                        </li>
                        <li>
                            <p><strong>Dati di utilizzo</strong></p>
                            <p>I dati di utilizzo sono i dati raccolti automaticamente generati dall'utilizzo del Servizio o dall'infrastruttura del Servizio stesso (ad esempio, la durata della visita di una pagina).</p>
                        </li>
                        <li>
                            <p><strong>Cookies</strong></p>
                            <p>I cookie sono piccoli file memorizzati sul vostro dispositivo (computer o dispositivo mobile).</p>
                        </li>
                    </ul>

                    <h2>Raccolta e uso delle informazioni</h2>
                    <p>Raccogliamo diversi tipi di informazioni per vari scopi, per fornire e migliorare il nostro servizio.</p>

                    <h3>Tipologie di Dati raccolti</h3>

                    <h4>Dati personali</h4>
                    <p>Durante l'utilizzo del nostro Servizio, potremmo chiedervi di fornirci alcune informazioni di identificazione personale che possono essere utilizzate per contattarvi o identificarvi ("Dati personali"). Le informazioni di identificazione personale possono includere, ma non sono limitate a:</p>

                    <ul>
                        <li>Indirizzo email</li>    <li>Nome e cognome</li>            <li>Cookie e dati di utilizzo</li>
                    </ul>

                    <h4>Dati di utilizzo</h4>

                    <p>Potremmo anche raccogliere informazioni su come l'utente accede e utilizza il Servizio ("Dati di utilizzo"). Questi Dati di utilizzo possono includere informazioni quali l'indirizzo del protocollo Internet del computer (ad es. Indirizzo IP), il tipo di browser, la versione del browser, le pagine del nostro servizio che si visita, l'ora e la data della visita, il tempo trascorso su tali pagine, identificatore unico del dispositivo e altri dati diagnostici.</p>

                    <h4>Tracciamento; dati dei cookie</h4>
                    <p>Utilizziamo cookie e tecnologie di tracciamento simili per tracciare l'attività sul nostro Servizio e conservare determinate informazioni.</p>
                    <p>I cookie sono file con una piccola quantità di dati che possono includere un identificatore univoco anonimo. I cookie vengono inviati al vostro browser da un sito web e memorizzati sul vostro dispositivo. Altre tecnologie di tracciamento utilizzate sono anche beacon, tag e script per raccogliere e tenere traccia delle informazioni e per migliorare e analizzare il nostro Servizio.</p>
                    <p>Potete chiedere al vostro browser di rifiutare tutti i cookie o di indicare quando viene inviato un cookie. Tuttavia, se non si accettano i cookie, potrebbe non essere possibile utilizzare alcune parti del nostro Servizio. Puoi saperne di più su come gestire i cookie nella <a href="https://privacypolicies.com/blog/how-to-delete-cookies/">Guida ai cookie del browser</a>.</p>
                    <p>Esempi di cookie che utilizziamo:</p>
                    <ul>
                        <li><strong>Cookie di sessione.</strong> Utilizziamo i cookie di sessione per gestire il nostro servizio.</li>
                        <li><strong>Cookie di preferenza.</strong> Utilizziamo i cookie di preferenza per ricordare le vostre preferenze e varie impostazioni.</li>
                        <li><strong>Cookie di sicurezza.</strong> Utilizziamo i cookie di sicurezza per motivi di sicurezza.</li>
                    </ul>

                    <h2>Uso dei dati</h2>
                    <p>ShoppingLesto utilizza i dati raccolti per vari scopi:</p>
                    <ul>
                        <li>Per fornire e mantenere il nostro Servizio</li>
                        <li>Per comunicare agli utenti variazioni apportate al servizio che offriamo</li>
                        <li>Per permettere agli utenti di fruire, a propria discrezione, di funzioni interattive del nostro servizio</li>
                        <li>Per fornire un servizio ai clienti</li>
                        <li>Per raccogliere analisi o informazioni preziose in modo da poter migliorare il nostro Servizio</li>
                        <li>Per monitorare l'utilizzo del nostro Servizio</li>
                        <li>Per rilevare, prevenire e affrontare problemi tecnici</li>
                    </ul>

                    <h2>Trasferimento dei dati</h2>
                    <p>Le vostre informazioni, compresi i Dati personali, possono essere trasferite a - e mantenute su - computer situati al di fuori del vostro stato, provincia, nazione o altra giurisdizione governativa dove le leggi sulla protezione dei dati possono essere diverse da quelle della vostra giurisdizione.</p>
                    <p>Se ci si trova al di fuori di Italy e si sceglie di fornire informazioni a noi, si ricorda che trasferiamo i dati, compresi i dati personali, in Italy e li elaboriamo lì.</p>
                    <p>Il vostro consenso alla presente Informativa sulla privacy seguito dall'invio di tali informazioni rappresenta il vostro consenso al trasferimento.</p>
                    <p>ShoppingLesto adotterà tutte le misure ragionevolmente necessarie per garantire che i vostri dati siano trattati in modo sicuro e in conformità con la presente Informativa sulla privacy e nessun trasferimento dei vostri Dati Personali sarà effettuato a un'organizzazione o a un paese a meno che non vi siano controlli adeguati dei vostri dati e altre informazioni personali.</p>

                    <h2>Divulgazione di dati</h2>

                    <h3>Prescrizioni di legge</h3>
                    <p>ShoppingLesto può divulgare i vostri Dati personali in buona fede, ritenendo che tale azione sia necessaria per:</p>
                    <ul>
                        <li>Rispettare un obbligo legale</li>
                        <li>Proteggere e difendere i diritti o la proprietà di ShoppingLesto</li>
                        <li>Prevenire o investigare possibili illeciti in relazione al Servizio</li>
                        <li>Proteggere la sicurezza personale degli utenti del Servizio o del pubblico</li>
                        <li>Proteggere contro la responsabilità legale</li>
                    </ul>

                    <h2>Sicurezza dei dati</h2>
                    <p>La sicurezza dei vostri dati è importante per noi, ma ricordate che nessun metodo di trasmissione su Internet o metodo di archiviazione elettronica è sicuro al 100%. Pertanto, anche se adotteremo ogni mezzo commercialmente accettabile per proteggere i vostri Dati personali, non possiamo garantirne la sicurezza assoluta.</p>

                    <h2>Fornitori di servizi</h2>
                    <p>Potremmo impiegare società e individui di terze parti per facilitare il nostro Servizio ("Fornitori di servizi"), per fornire il Servizio per nostro conto, per eseguire servizi relativi ai Servizi o per aiutarci ad analizzare come viene utilizzato il nostro Servizio.</p>
                    <p>Le terze parti hanno accesso ai vostri Dati personali solo per eseguire queste attività per nostro conto e sono obbligate a non rivelarle o utilizzarle per altri scopi.</p>



                    <h2>Link ad altri siti</h2>
                    <p>OIl nostro servizio può contenere collegamenti ad altri siti non gestiti da noi. Cliccando su un link di terze parti, sarete indirizzati al sito di quella terza parte. Ti consigliamo vivamente di rivedere l'Informativa sulla privacy di ogni sito che visiti.</p>
                    <p>Non abbiamo alcun controllo e non ci assumiamo alcuna responsabilità per il contenuto, le politiche sulla privacy o le pratiche di qualsiasi sito o servizio di terzi.</p>


                    <h2>Privacy dei minori</h2>
                    <p>Il nostro servizio non si rivolge a minori di 18 anni ("Bambini").</p>
                    <p>Non raccogliamo consapevolmente informazioni personali relative a utenti di età inferiore a 18 anni. Se siete un genitore o tutore e siete consapevoli che vostro figlio ci ha fornito Dati personali, vi preghiamo di contattarci. Se veniamo a conoscenza del fatto che abbiamo raccolto Dati personali da minori senza la verifica del consenso dei genitori, adotteremo provvedimenti per rimuovere tali informazioni dai nostri server.</p>


                    <h2>Modifiche alla presente informativa sulla privacy</h2>
                    <p>Potremmo aggiornare periodicamente la nostra Informativa sulla privacy. Ti informeremo di eventuali modifiche pubblicando la nuova Informativa sulla privacy in questa pagina.</p>
                    <p>Vi informeremo via e-mail e / o un avviso di rilievo sul nostro Servizio, prima che la modifica diventi effettiva e aggiorneremo la "data di validità" nella parte superiore di questa Informativa sulla privacy.</p>
                    <p>Si consiglia di rivedere periodicamente la presente Informativa sulla privacy per eventuali modifiche. Le modifiche a tale informativa sulla privacy entrano in vigore nel momento in cui vengono pubblicate su questa pagina.</p>


                    <h2>Contattaci</h2>
                    <p>In caso di domande sulla presente Informativa sulla privacy, si prega di contattarci:</p>
                    <ul>
                        <li>Tramite e-mail: shoppinglesto@gmail.com</li>

                    </ul>
                </c:when>
                <c:otherwise>
                    <h1>Privacy Policy</h1>


                    <p>Effective date: November 18, 2018</p>


                    <p>ShoppingLesto ("us", "we", or "our") operates the shoppinglesto.alessandrogerevini.com website (hereinafter referred to as the "Service").</p>

                    <p>This page informs you of our policies regarding the collection, use, and disclosure of personal data when you use our Service and the choices you have associated with that data. Our Privacy Policy  for ShoppingLesto is managed with the help of <a href="https://privacypolicies.com/free-privacy-policy-generator/">Privacy Policies</a>.</p>

                    <p>We use your data to provide and improve the Service. By using the Service, you agree to the collection and use of information in accordance with this policy. Unless otherwise defined in this Privacy Policy, the terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, accessible from shoppinglesto.alessandrogerevini.com</p>


                    <h2>Information Collection And Use</h2>

                    <p>We collect several different types of information for various purposes to provide and improve our Service to you.</p>

                    <h3>Types of Data Collected</h3>

                    <h4>Personal Data</h4>

                    <p>While using our Service, we may ask you to provide us with certain personally identifiable information that can be used to contact or identify you ("Personal Data"). Personally identifiable information may include, but is not limited to:</p>

                    <ul>
                        <li>Email address</li><li>First name and last name</li><li>Cookies and Usage Data</li>
                    </ul>

                    <h4>Usage Data</h4>

                    <p>We may also collect information on how the Service is accessed and used ("Usage Data"). This Usage Data may include information such as your computer's Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that you visit, the time and date of your visit, the time spent on those pages, unique device identifiers and other diagnostic data.</p>

                    <h4>Tracking & Cookies Data</h4>
                    <p>We use cookies and similar tracking technologies to track the activity on our Service and hold certain information.</p>
                    <p>Cookies are files with small amount of data which may include an anonymous unique identifier. Cookies are sent to your browser from a website and stored on your device. Tracking technologies also used are beacons, tags, and scripts to collect and track information and to improve and analyze our Service.</p>
                    <p>You can instruct your browser to refuse all cookies or to indicate when a cookie is being sent. However, if you do not accept cookies, you may not be able to use some portions of our Service. You can learn more how to manage cookies in the <a href="https://privacypolicies.com/blog/how-to-delete-cookies/">Browser Cookies Guide</a>.</p>
                    <p>Examples of Cookies we use:</p>
                    <ul>
                        <li><strong>Session Cookies.</strong> We use Session Cookies to operate our Service.</li>
                        <li><strong>Preference Cookies.</strong> We use Preference Cookies to remember your preferences and various settings.</li>
                        <li><strong>Security Cookies.</strong> We use Security Cookies for security purposes.</li>
                    </ul>

                    <h2>Use of Data</h2>

                    <p>ShoppingLesto uses the collected data for various purposes:</p>
                    <ul>
                        <li>To provide and maintain the Service</li>
                        <li>To notify you about changes to our Service</li>
                        <li>To allow you to participate in interactive features of our Service when you choose to do so</li>
                        <li>To provide customer care and support</li>
                        <li>To provide analysis or valuable information so that we can improve the Service</li>
                        <li>To monitor the usage of the Service</li>
                        <li>To detect, prevent and address technical issues</li>
                    </ul>

                    <h2>Transfer Of Data</h2>
                    <p>Your information, including Personal Data, may be transferred to — and maintained on — computers located outside of your state, province, country or other governmental jurisdiction where the data protection laws may differ than those from your jurisdiction.</p>
                    <p>If you are located outside Italy and choose to provide information to us, please note that we transfer the data, including Personal Data, to Italy and process it there.</p>
                    <p>Your consent to this Privacy Policy followed by your submission of such information represents your agreement to that transfer.</p>
                    <p>ShoppingLesto will take all steps reasonably necessary to ensure that your data is treated securely and in accordance with this Privacy Policy and no transfer of your Personal Data will take place to an organization or a country unless there are adequate controls in place including the security of your data and other personal information.</p>

                    <h2>Disclosure Of Data</h2>

                    <h3>Legal Requirements</h3>
                    <p>ShoppingLesto may disclose your Personal Data in the good faith belief that such action is necessary to:</p>
                    <ul>
                        <li>To comply with a legal obligation</li>
                        <li>To protect and defend the rights or property of ShoppingLesto</li>
                        <li>To prevent or investigate possible wrongdoing in connection with the Service</li>
                        <li>To protect the personal safety of users of the Service or the public</li>
                        <li>To protect against legal liability</li>
                    </ul>

                    <h2>Security Of Data</h2>
                    <p>The security of your data is important to us, but remember that no method of transmission over the Internet, or method of electronic storage is 100% secure. While we strive to use commercially acceptable means to protect your Personal Data, we cannot guarantee its absolute security.</p>

                    <h2>Service Providers</h2>
                    <p>We may employ third party companies and individuals to facilitate our Service ("Service Providers"), to provide the Service on our behalf, to perform Service-related services or to assist us in analyzing how our Service is used.</p>
                    <p>These third parties have access to your Personal Data only to perform these tasks on our behalf and are obligated not to disclose or use it for any other purpose.</p>



                    <h2>Links To Other Sites</h2>
                    <p>Our Service may contain links to other sites that are not operated by us. If you click on a third party link, you will be directed to that third party's site. We strongly advise you to review the Privacy Policy of every site you visit.</p>
                    <p>We have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.</p>


                    <h2>Children's Privacy</h2>
                    <p>Our Service does not address anyone under the age of 18 ("Children").</p>
                    <p>We do not knowingly collect personally identifiable information from anyone under the age of 18. If you are a parent or guardian and you are aware that your Children has provided us with Personal Data, please contact us. If we become aware that we have collected Personal Data from children without verification of parental consent, we take steps to remove that information from our servers.</p>


                    <h2>Changes To This Privacy Policy</h2>
                    <p>We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page.</p>
                    <p>We will let you know via email and/or a prominent notice on our Service, prior to the change becoming effective and update the "effective date" at the top of this Privacy Policy.</p>
                    <p>You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.</p>


                    <h2>Contact Us</h2>
                    <p>If you have any questions about this Privacy Policy, please contact us:</p>
                    <ul>
                        <li>By email: shoppinglesto@gmail.com</li>

                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-2 col-lg-3">
        </div>
    </div>
</div>
    <%@include file="parts/_footer.jspf" %>
    <%@include file="parts/_importsjs.jspf" %>
</body>

</html>