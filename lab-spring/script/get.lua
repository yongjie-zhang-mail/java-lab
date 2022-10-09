wrk.method = "POST"
wrk.body = "bodyStr=O5zQx1VcZIP7%2BCFC2WpySdPxnSbOot50I2%2FTZhiAighdeKz9qaa4lCY6Qxa2KtXtWEaJ717%2ByvrzbnLtQzd%2BsQiyjNc4L%2B0C2JP%2FKmrSB879pbX0UQ3W6fl4T8%2BymwkS"
wrk.headers["Content-Type"] = "application/x-www-form-urlencoded"

-- 查看是否正常响应；实际压测时注释掉；
function response(status, headers, body)
    if status == 200 then
        print(body)
    end
end


