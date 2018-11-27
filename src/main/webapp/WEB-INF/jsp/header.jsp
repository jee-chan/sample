<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
  <!-- navbar -->
  <nav class="navbar navbar-inverse">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${pageContext.request.contextPath}/main/">Rakus Items</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <div>
    <!--       	ログイン状態に応じて表示するボタンを変更する -->
            <ul class="nav navbar-nav navbar-right">
              <c:if test="${useraccount == null && adminaccount == null}" >
                  <li><a id="logout" href="${pageContext.request.contextPath}/userLogin/">Login <i class="fa fa-power-off"></i></a></li>
              </c:if>
              
              <c:if test="${useraccount != null || adminaccount != null}" >
                  <li><a id="logout" href="${pageContext.request.contextPath}/logout">Logout <i class="fa fa-power-off"></i></a></li>
              </c:if>
    
            </ul>
    <!--         ログインユーザ情報の表示 -->
            <p class="navbar-text navbar-right">
                <c:if test="${useraccount == null && adminaccount == null}" >
                      <span id="loginName">user: Not logged in</span>
                </c:if>
                <c:if test="${useraccount != null}" >
                      <span id="loginName">user: <c:out value="${useraccount.username}"/></span>
              </c:if>
              <c:if test="${adminaccount != null}" >
                <span id="loginName">admin: <c:out value="${adminaccount.username}"/></span>
              </c:if>
            </p>
    <!--    商品リストのダウンロードリンク（別タブで開かせるためaタグの機能は切っています） -->
              <c:if test="${adminaccount != null}" >
                <a class="navbar-text navbar-right" href='javascript:void(0)'>
                  <span id="csvDownload">ItemListDownload</span>
                </a>
              </c:if>
    <!--    ユーザリストページ遷移のためのリンク -->
              <c:if test="${adminaccount != null}" >
                <a class="navbar-text navbar-right" href='${pageContext.request.contextPath}/userList/'>
                  <span id="userList">UserList</span>
                </a>
              </c:if>
          </div>
        </div>
      </nav>
</body>