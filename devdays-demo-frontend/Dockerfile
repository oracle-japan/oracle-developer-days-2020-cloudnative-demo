FROM ghcr.io/graalvm/graalvm-ce:latest

# GraalVMのnpmバイナリをPATHに追加
ENV PATH $PATH:$JAVA_HOME/bin/npm

# ディレクトリ作成
WORKDIR /app

# パッケージをコピー
COPY package*.json ./

# npm モジュールをインストール
RUN npm install --quiet

# 成果物コピー
COPY . .

# このコマンドをしないといけないとwarnが出たので
RUN npm rebuild

# 本当はいらないが開発環境でvue-cliを使っていたのでそこに含まれているパッケージを使っているようでwarnが出たので入れる
RUN npm install vue-cli -g

# ビルド
RUN npm run build

# なくても良い
ENV HOST 0.0.0.0
# なくても良い
EXPOSE 3000

# 起動
CMD ["npm", "run", "start"]
